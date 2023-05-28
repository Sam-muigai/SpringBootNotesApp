package com.sam.springbootnotesapp.feature_authentication.presentation.sign_up

data class SignUpState(
    val emailError:String = "",
    val passwordError:String = "",
    val confirmPasswordError:String = "",
    val showPassword:Boolean = false,
    val showConfirmPassword:Boolean = false,
    val loading:Boolean = false,
    val signedIn:Boolean = false
)