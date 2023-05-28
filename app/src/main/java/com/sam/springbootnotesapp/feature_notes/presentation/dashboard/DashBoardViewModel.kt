package com.sam.springbootnotesapp.feature_notes.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.UseCases
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.toCategory
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import com.sam.springbootnotesapp.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val useCases: UseCases,
    private val authentication: EmailPasswordAuthentication
) : ViewModel() {

    private var _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    val initials = authentication.userEmail.toCharArray()[0].toString()

    val categories = listOf(Category.ALL, Category.CODING, Category.MUSIC, Category.STUDIES)

    var selectedCategory by mutableStateOf(categories[0].convertToCategoryItem().text)

    init {
        getAllNotes()
    }

    val photoUrl = Firebase.auth.currentUser?.photoUrl

    private fun getAllNotes() {
        viewModelScope.launch {
            useCases.getAllNotes(authentication.userEmail)
                .collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            val data = if (selectedCategory.toCategory() == Category.ALL)
                                dataState.data
                            else
                                dataState.data?.filter { it.category == selectedCategory }
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                data = data ?: emptyList()
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                loading = false
                            )
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                loading = true
                            )
                        }
                    }
                }
        }
    }


    fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            useCases.deleteNoteById(id).collect {
                when (it) {
                    is DataState.Loading -> Unit
                    is DataState.Error -> {
                        _uiEvents.emit(UiEvents.ShowSnackBar(it.message ?: ""))
                    }

                    is DataState.Success -> {
                        _uiEvents.emit(UiEvents.ShowSnackBar(it.data ?: ""))
                    }
                }
            }
        }
    }

    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onSortClicked() {
        _uiState.value = _uiState.value.copy(
            sortOrderShow = !uiState.value.sortOrderShow
        )
        if (!uiState.value.sortOrderShow){
            selectedCategory = Category.ALL.convertToCategoryItem().text
        }
    }

    fun onCategoryChange(category: Category) {
        selectedCategory = category.convertToCategoryItem().text
        getAllNotes()
    }


}