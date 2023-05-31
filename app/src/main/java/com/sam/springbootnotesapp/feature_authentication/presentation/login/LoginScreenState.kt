package com.sam.springbootnotesapp.feature_authentication.presentation.login

data class LoginScreenState(
    val emailError:String = "",
    val passwordError:String = "",
    val loading:Boolean = false,
    val showPassword:Boolean = false,
    val signedIn:Boolean = false
)