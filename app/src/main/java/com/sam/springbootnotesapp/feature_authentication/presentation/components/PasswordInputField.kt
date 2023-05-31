package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import com.sam.springbootnotesapp.ui.theme.lato



@Composable
fun PasswordInput(
    text: String,
    modifier: Modifier,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    onEyeClicked: () -> Unit,
    placeHolder: String = ""
) {
    val (passwordVisibility, icon) = if (showPassword)
        Pair(VisualTransformation.None, R.drawable.icon_eye_hide)
    else
        Pair(PasswordVisualTransformation(), R.drawable.icon_eye)
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
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        onValueChange = onValueChange,
        visualTransformation = passwordVisibility,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                if (text.isEmpty()) {
                    TextFieldContent {
                        Text(
                            text = placeHolder,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                TextFieldContent {
                    innerTextField()
                    if (text.isNotEmpty()) {
                        IconEye(
                            modifier = Modifier.size(23.dp)
                                .testTag("eye_icon"),
                            icon = icon,
                            onEyeClicked = onEyeClicked
                        )
                    } else Box(modifier = Modifier)
                }
            }
        }
    )
}


@Composable
fun IconEye(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onEyeClicked: () -> Unit
) {
    Icon(
        modifier = modifier
            .clickable {
                onEyeClicked()
            },
        painter = painterResource(id = icon),
        contentDescription = "visible icon",
        tint = Color.White
    )
}

@Preview
@Composable
fun PasswordInputFieldPreview() {
    SpringBootNotesAppTheme {

    }
}