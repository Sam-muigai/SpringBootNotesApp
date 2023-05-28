package com.sam.springbootnotesapp.feature_notes.presentation.add_note

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.UseCases
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import com.sam.springbootnotesapp.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val useCases: UseCases,
    private val emailPasswordAuthentication: EmailPasswordAuthentication,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _state = MutableStateFlow(AddNoteState())
    val state = _state.asStateFlow()

    private var _uiEvent = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvent.asSharedFlow()

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var categories = listOf(Category.CODING, Category.MUSIC, Category.STUDIES)
        private set

    var expanded = mutableStateOf(false)

    var selectedText by mutableStateOf(categories[0].convertToCategoryItem().text)


    var heading by mutableStateOf("Add Note")

    private var userId: Int? by mutableStateOf(null)

    init {
        val id = savedStateHandle.get<Int>("id")!!
        if (id != -1) {
            viewModelScope.launch {
                heading = "Update Note"
                val noteClicked = useCases.getNoteById(id)
                title = noteClicked.title
                description = noteClicked.description
                selectedText = noteClicked.category
                userId = noteClicked.id
            }
        }
    }

    fun onEvent(events: AddNoteEvents) {
        when (events) {
            is AddNoteEvents.OnTitleValueChange -> {
                title = events.value
            }

            is AddNoteEvents.OnDescriptionValueChange -> {
                description = events.value
            }

            is AddNoteEvents.OnExpandedChange -> {
                expanded.value = !expanded.value
            }

            is AddNoteEvents.OnDismissRequest -> {
                expanded.value = false
            }

            is AddNoteEvents.OnItemClicked -> {
                selectedText = events.category
                expanded.value = false
            }

            is AddNoteEvents.OnConfirmClicked -> {
                viewModelScope.launch {
                    if (description.isNotEmpty() && title.isNotEmpty()) {
                        val note = Notes(
                            category = selectedText,
                            description = description,
                            email = emailPasswordAuthentication.userEmail,
                            id = userId,
                            title = title,
                            synch = synch
                        )
                        useCases.addNote(note).collect {
                            when (it) {
                                is DataState.Loading -> {
                                    _state.value = _state.value.copy(
                                        loading = true
                                    )
                                }

                                is DataState.Success -> {
                                    delay(500L)
                                    _uiEvent.emit(UiEvents.ShowSnackBar(it.data ?: ""))
                                    _uiEvent.emit(UiEvents.PopBackStack)
                                }

                                is DataState.Error -> {
                                    _state.value = _state.value.copy(
                                        loading = false
                                    )
                                    _uiEvent.emit(UiEvents.ShowSnackBar(it.message ?: ""))
                                }
                            }
                        }
                    } else _uiEvent.emit(UiEvents.ShowSnackBar("Title and description cannot be empty!"))
                }
            }
        }
    }
}

enum class Category {
    CODING, MUSIC, STUDIES, ALL;

    companion object {
        fun Category.convertToCategoryItem(): CategoryItem {
            return when (this) {
                CODING -> CategoryItem(R.drawable.ic_computer, "CODING", Color.Red)
                MUSIC -> CategoryItem(R.drawable.ic_music, "MUSIC", Color.Blue)
                STUDIES -> CategoryItem(R.drawable.ic_book, "STUDIES", Color.Green)
                ALL -> CategoryItem(R.drawable.ic_book, "ALL", Color.Gray)
            }
        }

        fun String.toCategory(): Category {
            return when (this) {
                "CODING" -> CODING
                "MUSIC" -> MUSIC
                "ALL" -> ALL
                else -> STUDIES
            }
        }
    }
}

data class CategoryItem(
    @DrawableRes val icon: Int,
    val text: String,
    val color: Color
)

