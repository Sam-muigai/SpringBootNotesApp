package com.sam.springbootnotesapp.feature_authentication.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.util.Routes
import com.sam.springbootnotesapp.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val emailPasswordAuthentication: EmailPasswordAuthentication
):ViewModel(){

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    val imageUrl = Firebase.auth.currentUser?.photoUrl

    val email = emailPasswordAuthentication.userEmail

    val initials = email.toCharArray()[0].toString()

    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            emailPasswordAuthentication.signOut()
            withContext(Dispatchers.Main){
                _uiEvents.emit(UiEvents.Navigate(Routes.AUTHENTICATION))
            }
        }
    }

}