package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sam.springbootnotesapp.ui.theme.lato

@Composable
fun RegisterButton(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
    content: @Composable () -> Unit
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFF8875FF)),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    ) {
       content.invoke()
    }
}