package com.sam.springbootnotesapp.feature_notes.presentation.search_note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.DetailInput
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.toCategory
import com.sam.springbootnotesapp.feature_notes.presentation.components.LoadingTodoItem
import com.sam.springbootnotesapp.feature_notes.presentation.components.NoteItem
import com.sam.springbootnotesapp.ui.theme.lato

@Composable
fun SearchScreen(
    viewModel: SearchNoteViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (Int) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    SearchScreenContent(
        searchTerm = viewModel.searchTerm.value,
        onSearchTermChange = viewModel::onSearch,
        state = state,
        focusRequester = focusRequester,
        onDeleteClicked = viewModel::onDelete,
        onBackClicked = onBackClicked,
        onNoteClicked = onDeleteClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchTerm: String,
    onNoteClicked: (Int) -> Unit,
    onSearchTermChange: (String) -> Unit,
    state: SearchNoteState,
    focusRequester: FocusRequester,
    onBackClicked: () -> Unit,
    onDeleteClicked: (Int) -> Unit
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            onBackClicked.invoke()
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_notes),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = lato
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    DetailInput(
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester),
                        placeHolder = stringResource(id = R.string.search),
                        text = searchTerm,
                        onValueChange = onSearchTermChange
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                AnimatedVisibility(
                    state.loading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier.testTag("loading")
                    ) {
                        items(5) {
                            LoadingTodoItem()
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.testTag("data")
                ) {
                    items(state.data) { note ->
                        val category = note.category.toCategory().convertToCategoryItem()
                        NoteItem(
                            modifier = Modifier,
                            title = note.title,
                            icon = category.icon,
                            description = note.description,
                            category = category.text,
                            onClick = {
                                note.id?.let {
                                    onNoteClicked(it)
                                }
                            },
                            color = category.color,
                            onDeleteClicked = {
                                note.id?.let { onDeleteClicked(it) }
                            }
                        )
                    }
                }
            }
        }
    }
}