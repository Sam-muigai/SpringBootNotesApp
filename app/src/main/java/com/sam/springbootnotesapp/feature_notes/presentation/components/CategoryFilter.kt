package com.sam.springbootnotesapp.feature_notes.presentation.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import com.sam.springbootnotesapp.ui.theme.lato

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFilter(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategory: Category,
    onRadioClicked: (Category) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlowRow(
            maxItemsInEachRow = 3
        ) {
            categories.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = it == selectedCategory,
                        onClick = {
                            onRadioClicked(it)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = it.convertToCategoryItem().color
                        )
                    )
                    Text(
                        text = it.convertToCategoryItem().text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryFilter() {
    SpringBootNotesAppTheme {
        CategoryFilter(
            categories = listOf(Category.STUDIES, Category.MUSIC, Category.CODING),
            selectedCategory = Category.CODING,
            onRadioClicked = {}
        )
    }
}


