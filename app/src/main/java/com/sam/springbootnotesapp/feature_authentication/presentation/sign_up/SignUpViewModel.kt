package com.sam.springbootnotesapp.feature_authentication.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.springbootnotesapp.feature_authentication.presentation.ConfirmPasswordValidationException
import com.sam.springbootnotesapp.feature_authentication.presentation.EmailValidationException
import com.sam.springbootnotesapp.feature_authentication.presentation.PasswordValidationException
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication.GoogleAuthClient
import com.sam.springbootnotesapp.feature_authentication.presentation.verifyConfirmPassword
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
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authentication: EmailPasswordAuthentication,
    private val googleAuthClient: GoogleAuthClient
):ViewModel() {

    private var _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    val isSignedIn = googleAuthClient.isSignedIn(viewModelScope)

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    fun onEvent(events: SignUpEvents){
        when(events){
            is SignUpEvents.OnEmailChange ->{
                email = events.input
                if (email.isNotEmpty()){
                    try {
                        verifyEmail(email)
                        _state.value = _state.value.copy(
                            emailError = ""
                        )
                    }catch (e:EmailValidationException){
                        e.message?.let {
                            _state.value = _state.value.copy(
                                emailError = it
                            )
                        }
                    }
                }else{
                    _state.value = _state.value.copy(
                        emailError = ""
                    )
                }
            }
            is SignUpEvents.OnPasswordChange ->{
                password = events.input
                if (password.isNotEmpty()){
                    try {
                        verifyPassword(password)
                        _state.value = _state.value.copy(
                            passwordError = ""
                        )
                    }catch (e:PasswordValidationException){
                        e.message?.let {
                            _state.value = _state.value.copy(
                                passwordError = it
                            )
                        }
                    }
                }else{
                    _state.value = _state.value.copy(
                        passwordError = ""
                    )
                }
            }
            is SignUpEvents.OnConfirmPasswordChange ->{
                confirmPassword = events.input
                if (confirmPassword.isNotEmpty()){
                    try {
                        verifyConfirmPassword(password,confirmPassword)
                        _state.value = _state.value.copy(
                            confirmPasswordError = ""
                        )
                    }catch (e:ConfirmPasswordValidationException){
                        e.message?.let {
                            _state.value = _state.value.copy(
                                confirmPasswordError = it
                            )
                        }
                    }
                }else{
                    _state.value = _state.value.copy(
                        confirmPasswordError = ""
                    )
                }
            }
            is SignUpEvents.OnSignUpClicked ->{
                _state.value = _state.value.copy(
                    loading = true
                )
               viewModelScope.launch {
                   try {
                       val userEmail = verifyEmail(email)
                       val userPassword = verifyPassword(password)
                       val auth = authentication.signUpWithEmailAndPassword(userEmail,userPassword)
                       if (auth.user != null){
                           // TODO: Navigate to dashboard
                           emitEvents(UiEvents.Navigate(Routes.DASHBOARD))
                           _state.value = _state.value.copy(
                              signedIn = true
                           )
                           emitEvents(UiEvents.ShowSnackBar("Sign Up Successful"))
                       }else{
                           _state.value = _state.value.copy(
                               loading = false
                           )
                       }
                   }catch (e:Exception){
                       e.message?.let {
                           emitEvents(UiEvents.ShowSnackBar(it))
                       }
                       _state.value = _state.value.copy(
                           loading = false
                       )
                   }
               }
            }
            is SignUpEvents.OnPasswordVisibleIconClicked ->{
                _state.value = _state.value.copy(
                    showPassword = !state.value.showPassword
                )
            }
            is SignUpEvents.OnConfirmPasswordVisibleIconClicked ->{
                _state.value = _state.value.copy(
                    showConfirmPassword = !state.value.showConfirmPassword
                )
            }
            is SignUpEvents.OnBackClicked ->{
                emitEvents(UiEvents.PopBackStack)
            }
        }
    }



    private fun emitEvents(event: UiEvents){
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }




}