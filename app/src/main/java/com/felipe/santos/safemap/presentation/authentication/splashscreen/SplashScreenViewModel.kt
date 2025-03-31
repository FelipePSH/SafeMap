package com.felipe.santos.safemap.presentation.authentication.splashscreen
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipe.santos.safemap.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataStore: DataStoreManager
) : ViewModel(){

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.isApprovedFlow.collect { isApproved ->
                if (auth.currentUser != null && isApproved) {
                    _uiState.value = SplashUiState.Approved
                } else {
                    checkUserAccess()
                }
            }
        }
    }

    private fun checkUserAccess() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            auth.signInAnonymously()
                .addOnSuccessListener {
                    verifyAccess(it.user?.uid)
                }
            .addOnFailureListener { exception ->
                    Log.e("SplashScreenViewModel", "signInAnonymously failed", exception)
                    _uiState.value = SplashUiState.Error("Authentication failed")
            }
        } else {
            verifyAccess(currentUser.uid)
        }
    }

    private fun verifyAccess(uid: String?) {
        if (uid == null) {
            _uiState.value = SplashUiState.Error("UID is null")
            return
        }

        firestore.collection("approved_users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                viewModelScope.launch {
                    if (doc.exists()) {
                        dataStore.setApproved(true)
                        _uiState.value = SplashUiState.Approved
                    } else {
                        dataStore.setApproved(false)
                        _uiState.value = SplashUiState.NotApproved
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("SplashScreenViewModel", "Failed to get approved user document", exception)
                _uiState.value = SplashUiState.Error("Failed to verify user")
            }
    }

}