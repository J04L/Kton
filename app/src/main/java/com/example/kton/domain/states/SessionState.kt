package com.example.kton.domain.states

sealed class SessionState {
    object Loading : SessionState()
    object Authenticated : SessionState()
    object NotAuthenticated : SessionState()
}