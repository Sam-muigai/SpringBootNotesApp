package com.sam.springbootnotesapp.feature_notes.presentation.dashboard

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.DetailInput
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.toCategory
import com.sam.springbootnotesapp.feature_notes.presentation.components.CategoryFilter
import com.sam.springbootnotesapp.feature_notes.presentation.components.LoadingTodoItem
import com.sam.springbootnotesapp.feature_notes.presentation.components.NoteItem
import com.sam.springbootnotesapp.feature_notes.presentation.components.TopBar
import com.sam.springbootnotesapp.util.Routes
import com.sam.springbootnotesapp.util.UiEvents

@Composable
fun DashBoard(
    viewModel: DashBoardViewModel = hiltViewModel(),
    onAddNote: (String) -> Unit = {},
    context: Context,
    onSearchClicked: () -> Unit,
    onPhotoClicked: () -> Unit,
    onNoteClicked: (Int) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }
        }
    }
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    DashBoardContent(
        state = state,
        onAddNote = onAddNote,
        photoUrl = viewModel.photoUrl,
        initialChar = viewModel.initials,
        onDeleteClicked = viewModel::onDeleteClicked,
        onNoteClicked = onNoteClicked,
        onPhotoClicked = onPhotoClicked,
        onSearchClicked = onSearchClicked,
        categories = viewModel.categories,
        selectedText = viewModel.selectedCategory,
        onCategorySelect = viewModel::onCategoryChange,
        onSortClicked = viewModel::onSortClicked
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardContent(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onAddNote: (String) -> Unit,
    photoUrl: Uri?,
    selectedText: String,
    initialChar:String,
    categories: List<Category>,
    onPhotoClicked: () -> Unit,
    onSortClicked: () -> Unit,
    onCategorySelect: (Category) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit,
    onNoteClicked: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAddNote(Routes.ADD_NOTE)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_note),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(modifier = Modifier.padding(it)) {
                TopBar(
                    photoUrl = photoUrl,
                    initialLetter = initialChar,
                    onSortClicked = onSortClicked,
                    onPhotoClick = onPhotoClicked
                )
                AnimatedVisibility(visible = state.sortOrderShow) {
                    CategoryFilter(
                        categories = categories,
                        selectedCategory = selectedText.toCategory(),
                        onRadioClicked = onCategorySelect
                    )
                }
                AnimatedVisibility(visible = !state.sortOrderShow) {
                    DetailInput(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onSearchClicked() },
                        placeHolder = stringResource(id = R.string.search),
                        leadingIcon = Icons.Default.Search,
                        text = "",
                        enabled = false,
                        horizontalArrangement = Arrangement.Start,
                        onValueChange = {}
                    )
                }
                AnimatedVisibility(
                    state.loading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(contentPadding = PaddingValues(top = 30.dp, bottom = 50.dp)) {
                        items(5) {
                            LoadingTodoItem()
                        }
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(top = 30.dp, bottom = 50.dp)
                ) {
                    items(state.data) { note ->
                        val category = note.category.toCategory().convertToCategoryItem()
                        NoteItem(
                            title = note.title,
                            description = note.description,
                            color = category.color,
                            onDeleteClicked = {
                                note.id?.let { id ->
                                    onDeleteClicked(id)
                                }
                            },
                            onClick = {
                                onNoteClicked(note.id!!)
                            },
                            category = note.category,
                            icon = category.icon
                        )
                    }
                }
            }
        }
    }
}

