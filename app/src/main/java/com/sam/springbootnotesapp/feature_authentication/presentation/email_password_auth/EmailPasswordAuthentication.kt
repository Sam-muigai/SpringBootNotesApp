package com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth

import com.google.firebase.auth.AuthResult

interface EmailPasswordAuthentication {

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult

    val userEmail:String

    suspend fun signOut()

    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ):AuthResult
}