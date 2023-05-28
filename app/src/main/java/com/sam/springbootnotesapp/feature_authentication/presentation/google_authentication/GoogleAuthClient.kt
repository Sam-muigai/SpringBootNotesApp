package com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GoogleAuthClient {

    fun isSignedIn(viewModelScope: CoroutineScope):StateFlow<Boolean>

    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent):AuthResult?
}