package com.example.kton.domain.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kton.domain.repository.SessionRepository
import com.example.kton.domain.states.SessionState
import com.example.kton.utils.SecureTokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    val sessionRepository: SessionRepository,
    @ApplicationContext val context: Context
): ViewModel() {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState

    init {
        getUserSession()
    }

    fun getUserSession(){
        viewModelScope.launch{
            val token = SecureTokenStorage.getToken(context)
            if(token != null){
                val result = sessionRepository.userSession(token)
                if(result.isSuccess) {
                    _sessionState.value = SessionState.Authenticated
                    Log.d("fdaf", "dfaefafae")
                }
                else _sessionState.value = SessionState.NotAuthenticated
            }
            else _sessionState.value = SessionState.NotAuthenticated
        }
    }
}