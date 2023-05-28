package com.sam.springbootnotesapp.feature_authentication.presentation.login

sealed class LoginEvents
{
    data class OnEmailValueChange(val value:String):LoginEvents()
    data class OnPasswordChange(val value: String):LoginEvents()
    object OnLoginClicked:LoginEvents()
    object OnVisibleIconClicked:LoginEvents()
    object OnBackClicked:LoginEvents()
}