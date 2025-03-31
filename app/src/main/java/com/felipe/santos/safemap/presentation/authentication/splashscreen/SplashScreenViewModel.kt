package com.felipe.santos.safemap.presentation.splashscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel(){

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkUserAccess()
    }

    private fun checkUserAccess() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            auth.signInAnonymously()
                .addOnSuccessListener {
                    verifyAccess(it.user?.uid)
                }
                .addOnFailureListener {
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
                if (doc.exists()) {
                    _uiState.value = SplashUiState.Approved
                } else {
                    _uiState.value = SplashUiState.NotApproved
                }
            }
            .addOnFailureListener {
                _uiState.value = SplashUiState.Error("Failed to verify user")
            }
    }

}