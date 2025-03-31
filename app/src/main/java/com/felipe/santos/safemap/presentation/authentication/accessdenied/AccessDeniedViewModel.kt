package com.felipe.santos.safemap.presentation.authentication.accessdenied

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.felipe.santos.safemap.data.local.DataStoreManager
import com.felipe.santos.safemap.domain.usecase.ApproveUserWithInviteCodeUseCase
import com.felipe.santos.safemap.presentation.navigation.AccessDenied
import com.felipe.santos.safemap.presentation.navigation.Home
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessDeniedViewModel @Inject constructor(
    private val dataStore: DataStoreManager,
    private val useCase: ApproveUserWithInviteCodeUseCase,
    private val auth: FirebaseAuth
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
            val user = auth.currentUser
            if (user != null) {
                val result = useCase(inviteCode.value, user)
                if (result.isSuccess) {
                    dataStore.setApproved(true)
                    navController.navigate(Home) {
                        popUpTo(AccessDenied) { inclusive = true }
                    }
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Unknown error"
                }
            }
        }
    }


    fun clearError() {
        _errorMessage.value = null
    }
}
