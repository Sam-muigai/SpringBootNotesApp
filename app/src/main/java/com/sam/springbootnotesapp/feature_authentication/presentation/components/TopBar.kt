package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sam.springbootnotesapp.R

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onIconClick:() ->Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Icon(
            modifier = Modifier.size(25.dp)
                .clickable { onIconClick.invoke() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}