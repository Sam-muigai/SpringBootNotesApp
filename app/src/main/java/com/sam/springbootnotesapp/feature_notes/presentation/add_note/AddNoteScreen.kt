package com.sam.springbootnotesapp.feature_notes.presentation.add_note

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.DetailInput
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.components.CategoryChips
import com.sam.springbootnotesapp.ui.theme.lato
import com.sam.springbootnotesapp.util.UiEvents


@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    context: Context,
    popBack: () -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.uiEvents.collect {
                when (it) {
                    is UiEvents.PopBackStack -> {
                        popBack.invoke()
                    }

                    is UiEvents.ShowSnackBar -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    )
    AddNoteScreenContent(
        heading = viewModel.heading,
        title = viewModel.title,
        description = viewModel.description,
        onEvent = viewModel::onEvent,
        state = state,
        categories = viewModel.categories,
        selectedText = viewModel.selectedText
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreenContent(
    heading: String,
    title: String,
    description: String,
    state: AddNoteState,
    categories: List<Category>,
    selectedText: String,
    onEvent: (AddNoteEvents) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AddNoteEvents.OnConfirmClicked)
                }
            ) {
                if (state.loading) CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 1.dp
                )
                else Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.save)
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = heading,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                CategoryChips(
                    categories = categories,
                    onClicked = {
                        onEvent(AddNoteEvents.OnItemClicked(it.convertToCategoryItem().text))
                    },
                    selectedText = selectedText
                )
                Spacer(modifier = Modifier.height(10.dp))
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    singleLine = true,
                    onValueChange = {
                        onEvent(AddNoteEvents.OnTitleValueChange(it))
                    },
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ), decorationBox = { innerTextField ->
                        if (title.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.title),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    singleLine = false,
                    onValueChange = {
                        onEvent(AddNoteEvents.OnDescriptionValueChange(it))
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ), decorationBox = { innerTextField ->
                        if (description.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.description),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

