package com.sam.springbootnotesapp.feature_notes.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.ui.theme.lato

@Composable
fun CategoryChips(
    categories: List<Category>,
    onClicked: (Category) -> Unit,
    selectedText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val borderColor = if (selectedText == category.convertToCategoryItem().text)
                category.convertToCategoryItem().color
            else
                MaterialTheme.colorScheme.onSurface
            OutlinedButton(
                border = BorderStroke(2.dp, borderColor),
                colors = ButtonDefaults
                    .outlinedButtonColors(
                        containerColor = Color.Transparent
                    ),
                onClick = {
                    onClicked(category)
                }
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}