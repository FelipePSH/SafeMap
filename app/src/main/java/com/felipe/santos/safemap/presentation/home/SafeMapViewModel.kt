package com.felipe.santos.safemap.presentation.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipe.santos.safemap.domain.location.LocationProvider
import com.felipe.santos.safemap.domain.model.AlertModel
import com.felipe.santos.safemap.domain.repository.AlertRepository
import com.felipe.santos.safemap.domain.usecase.GetFilteredAlertsUseCase
import com.felipe.santos.safemap.presentation.model.AlertUiModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SafeMapViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val getFilteredAlertsUseCase: GetFilteredAlertsUseCase,
    private val alertRepository: AlertRepository
) : ViewModel() {

    private val _hasLocationPermission = MutableStateFlow(false)
    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    private val _alerts = MutableStateFlow<List<AlertModel>>(emptyList())
    val alerts: StateFlow<List<AlertModel>> = _alerts

    private val _selectedAlert = MutableStateFlow<AlertModel?>(null)
    val selectedAlert: StateFlow<AlertModel?> = _selectedAlert

    private val _selectedUiAlert = MutableStateFlow<AlertUiModel?>(null)
    val selectedUiAlert: StateFlow<AlertUiModel?> = _selectedUiAlert

    private val _uiAlerts = MutableStateFlow<List<AlertUiModel>>(emptyList())
    val uiAlerts: StateFlow<List<AlertUiModel>> = _uiAlerts

    private val _hasUserConfirmed = MutableStateFlow(false)
    val hasUserConfirmed: StateFlow<Boolean> = _hasUserConfirmed

    init {
        observeAlerts()
    }

    private fun observeAlerts() {
        viewModelScope.launch {
            getFilteredAlertsUseCase().collect { alerts ->
                _alerts.value = alerts
                _uiAlerts.value = alerts.map { it.toUiModel() }
            }
        }
    }

    private fun AlertModel.toUiModel(): AlertUiModel {
        val formattedTimestamp = try {
            val date = timestamp.toDate()
            val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            formatter.format(date)
        } catch (e: Exception) {
            "Unknown"
        }

        return AlertUiModel(
            type = this.type,
            description = this.description,
            timestampFormatted = formattedTimestamp,
            latitude = this.latitude,
            longitude = this.longitude,
            confirmations = this.confirmations
        )
    }

    fun updatePermissionStatus(granted: Boolean) {
        _hasLocationPermission.value = granted
        if (granted) fetchUserLocation()
    }

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    fun fetchUserLocation() {
        viewModelScope.launch {
            _location.value = locationProvider.getLastKnownLocation()
        }
    }

    fun selectAlert(alert: AlertModel) {
        _selectedAlert.value = alert
        _selectedUiAlert.value = alert.toUiModel()

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val confirmed = alertRepository.hasUserConfirmed(alert.id, userId)
            _hasUserConfirmed.value = confirmed
        }
    }

    fun clearSelectedAlert() {
        _selectedAlert.value = null
        _selectedUiAlert.value = null
    }

    fun witnessConfirmation() {
        viewModelScope.launch {
            val alert = _selectedAlert.value ?: return@launch
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            alertRepository.confirmAlert(alert.id, userId)
            _hasUserConfirmed.value = true
            clearSelectedAlert()
        }
    }
}