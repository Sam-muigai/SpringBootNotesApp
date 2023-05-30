package com.sam.springbootnotesapp.feature_notes.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.UseCases
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.toCategory
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import com.sam.springbootnotesapp.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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


    private fun getAllNotes(
        email: String = authentication.userEmail
    ) {
        viewModelScope.launch {
            useCases.getAllNotes(email)
                .collect { dataState ->
                    when (dataState) {
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                loading = true
                            )
                        }

                        is DataState.Error -> {
                            _uiEvents.emit(
                                UiEvents.ShowSnackBar(
                                    dataState.message ?: "An error occurred"
                                )
                            )
                        }

                        is DataState.Success -> {
                            val data = onCategoryChange(
                                selectedCategory.toCategory(),
                                dataState.data ?: emptyList()
                            )
                            _uiState.value = _uiState.value.copy(
                                data = data ,
                                loading = false
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
        if (!uiState.value.sortOrderShow) {
            selectedCategory = Category.ALL.convertToCategoryItem().text
        }
    }

    fun onCategoryChange(
        category: Category,
        notes: List<Notes> = emptyList()
    ): List<Notes> {
        selectedCategory = category.convertToCategoryItem().text
        return when (category) {
            Category.ALL -> notes
            else -> notes.filter {
                it.category == category.convertToCategoryItem().text
            }
        }
    }


}