package com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class EmailPasswordAuthenticationImpl : EmailPasswordAuthentication {

    private val auth = FirebaseAuth.getInstance()


    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = withContext(Dispatchers.IO) {
        auth.signInWithEmailAndPassword(email, password)
            .await()
    }

    override val userEmail: String
        get() = auth.currentUser?.email ?: ""

    override suspend fun signOut() = withContext(Dispatchers.IO){
        auth.signOut()
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ):AuthResult{
       return withContext(Dispatchers.IO){
           auth.createUserWithEmailAndPassword(email, password).await()
       }
    }
}