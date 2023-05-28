package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sam.springbootnotesapp.ui.theme.lato



@Composable
fun DetailInput(
    modifier: Modifier = Modifier,
    placeHolder: String,
    leadingIcon:ImageVector? = null,
    text: String,
    enabled:Boolean = true,
    height: Dp = 32.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Color(0XFF979797),
                RoundedCornerShape(4.dp)
            )
            .fillMaxWidth(),
        value = text,
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                if (text.isEmpty()) {
                    TextFieldContent(
                        height = height,
                        verticalAlignment = verticalAlignment,
                        horizontalArrangement = horizontalArrangement
                    ) {
                        if (leadingIcon != null){
                            Icon(imageVector = leadingIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = placeHolder,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                TextFieldContent(
                    height = height,
                    verticalAlignment = verticalAlignment,
                ) {
                    innerTextField()
                }
            }
        }
    )
}




@Composable
fun TextFieldContent(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    verticalAlignment:Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement:Arrangement.Horizontal = Arrangement.SpaceBetween ,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
                horizontal = 18.dp
            )
            .height(height),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        content()
    }
}