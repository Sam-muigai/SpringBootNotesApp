package com.sam.springbootnotesapp.feature_authentication.presentation.sign_up

sealed class SignUpEvents{
    data class OnEmailChange(val input:String):SignUpEvents()
    data class OnPasswordChange(val input:String):SignUpEvents()
    data class OnConfirmPasswordChange(val input: String):SignUpEvents()
    object OnPasswordVisibleIconClicked:SignUpEvents()
    object OnConfirmPasswordVisibleIconClicked:SignUpEvents()
    object OnSignUpClicked:SignUpEvents()
    object OnBackClicked:SignUpEvents()
}