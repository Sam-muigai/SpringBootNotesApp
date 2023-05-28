package com.sam.springbootnotesapp.feature_notes.presentation.search_note

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.UseCases
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val useCases: UseCases,
    private val userEmailPasswordAuthentication: EmailPasswordAuthentication
) : ViewModel() {

    private val userEmail = userEmailPasswordAuthentication.userEmail

    private var _searchTerm = mutableStateOf("")
    val searchTerm = _searchTerm

    private var _state = MutableStateFlow(SearchNoteState())
    val state = _state.asStateFlow()

    var searchJob: Job? = null

    fun onDelete(id:Int){
        viewModelScope.launch {
            useCases.deleteNoteById(id)
        }
    }

    fun onSearch(searchTerm: String) {
        _searchTerm.value = searchTerm.trim()
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            useCases.getNotesBySearch(userEmail, searchTerm).onEach {
                when (it) {
                    is DataState.Success -> {
                        _state.value = SearchNoteState(data = it.data ?: emptyList())
                    }

                    is DataState.Error -> {
                        _state.value = SearchNoteState()
                    }

                    is DataState.Loading -> {
                        _state.value = SearchNoteState(loading = true)
                    }
                }
            }.launchIn(this)
        }
    }


}