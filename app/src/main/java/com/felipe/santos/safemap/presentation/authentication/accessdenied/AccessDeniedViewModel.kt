package com.felipe.santos.safemap.presentation.authentication.accessdenied

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.felipe.santos.safemap.data.local.DataStoreManager
import com.felipe.santos.safemap.domain.repository.InviteRepository
import com.felipe.santos.safemap.presentation.navigation.AccessDenied
import com.felipe.santos.safemap.presentation.navigation.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AccessDeniedViewModel @Inject constructor(
    private val repository: InviteRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _inviteCode = MutableStateFlow("")
    val inviteCode: StateFlow<String> = _inviteCode

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onInviteCodeChange(newCode: String) {
        _inviteCode.value = newCode
    }

    fun submitCode(navController: NavController) {
        viewModelScope.launch {
            val result = repository.verifyInviteCode(_inviteCode.value)
            if (result) {
                dataStore.setApproved(true)

                navController.navigate(Home) {
                    popUpTo(AccessDenied) { inclusive = true }
                }
            } else {
                _errorMessage.value = "Invalid invite code. Please try again."
            }
        }
    }



    fun clearError() {
        _errorMessage.value = null
    }
}
