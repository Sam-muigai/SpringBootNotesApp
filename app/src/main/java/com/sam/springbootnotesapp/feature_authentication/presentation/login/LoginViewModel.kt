package com.sam.springbootnotesapp.feature_authentication.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.sam.springbootnotesapp.feature_authentication.presentation.EmailValidationException
import com.sam.springbootnotesapp.feature_authentication.presentation.PasswordValidationException
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication.GoogleAuthClient
import com.sam.springbootnotesapp.feature_authentication.presentation.verifyEmail
import com.sam.springbootnotesapp.feature_authentication.presentation.verifyPassword
import com.sam.springbootnotesapp.util.Routes
import com.sam.springbootnotesapp.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleAuthClient: GoogleAuthClient,
    private val authentication: EmailPasswordAuthentication
) : ViewModel() {

    private var _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun signInWithGoogle(authResult: AuthResult?) {
        if (authResult?.user != null) {
            _state.value = _state.value.copy(
                signedIn = false
            )
            emitEvents(UiEvents.Navigate(Routes.DASHBOARD))
            emitEvents(UiEvents.ShowSnackBar("Login successful."))
        }
    }

    fun onEvent(events: LoginEvents) {
        when (events) {
            is LoginEvents.OnPasswordChange -> {
                password = events.value
                if (password.isNotEmpty()) {
                    try {
                        verifyPassword(password)
                        _state.value = _state.value.copy(
                            passwordError = ""
                        )
                    } catch (e: Exception) {
                        e.message?.let {
                            _state.value = _state.value.copy(
                                passwordError = it
                            )
                        }
                    }
                } else {
                    _state.value = _state.value.copy(
                        passwordError = "",
                        showPassword = false
                    )
                }
            }

            is LoginEvents.OnEmailValueChange -> {
                email = events.value
                if (email.isNotEmpty()) {
                    try {
                        verifyEmail(email)
                        _state.value = _state.value.copy(
                            emailError = ""
                        )
                    } catch (e: Exception) {
                        e.message?.let {
                            _state.value = _state.value.copy(
                                emailError = it
                            )
                        }
                    }
                } else {
                    _state.value = _state.value.copy(
                        emailError = ""
                    )
                }
            }

            is LoginEvents.OnLoginClicked -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                    try {
                        val userEmail = verifyEmail(email)
                        val userPassword = verifyPassword(password)
                        val authentication = authentication.loginWithEmailAndPassword(
                            email = userEmail,
                            password = userPassword
                        )
                        if (authentication.user != null) {
                            // TODO: Add navigation to dashboard screen
                            emitEvents(UiEvents.Navigate(Routes.DASHBOARD))
                            emitEvents(UiEvents.ShowSnackBar("Login successful."))
                        } else {
                            _state.value = _state.value.copy(
                                loading = false
                            )
                        }
                    } catch (e: Exception) {
                        e.message?.let {
                            // TODO: show SnackBar of the error
                            emitEvents(UiEvents.ShowSnackBar(it))
                        }
                        _state.value = _state.value.copy(
                            loading = false
                        )
                    }
                }
            }

            is LoginEvents.OnVisibleIconClicked -> {
                _state.value = _state.value.copy(
                    showPassword = !_state.value.showPassword
                )
            }
            is LoginEvents.OnBackClicked -> {
                emitEvents(UiEvents.PopBackStack)
            }

        }
    }


    private fun emitEvents(event: UiEvents) {
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }

}