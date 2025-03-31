package com.felipe.santos.safemap.presentation.authentication.accessdenied

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccessDeniedViewModel @Inject constructor() : ViewModel(){
    fun submitInvitationCode(code: String) {
        // Valide o código, atualize o Firestore e realize a navegação se necessário
        Log.d("AccessDenied", "Código submetido: $code")
        // Implemente a lógica de verificação e, se válido, navegue para a próxima tela
    }
}
