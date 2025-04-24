package com.yeshuwahane.pupil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow<Boolean?>(null)
    val isSignedIn: StateFlow<Boolean?> = _isSignedIn

    init {
        viewModelScope.launch {
            _isSignedIn.value = authRepository.isUserSignedIn()
        }
    }
}
