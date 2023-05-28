package com.sam.springbootnotesapp.feature_notes.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.ui.theme.lato
import java.util.Locale

@Preview
@Composable
fun TopBar(
    photoUrl: Uri? = null,
    initialLetter:String = "",
    onPhotoClick: () -> Unit = {},
    onSortClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp)
                .clickable { onSortClicked.invoke() },
            painter = painterResource(id = R.drawable.sort),
            contentDescription = stringResource(id = R.string.sort)
        )
        Text(
            text = stringResource(id = R.string.spring_notes),
            style = MaterialTheme.typography.titleSmall
        )
        if (photoUrl != null)
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable { onPhotoClick.invoke() },
                model = photoUrl.toString(),
                contentDescription = null
            )
        else
            Surface(modifier = Modifier
                .size(50.dp)
                .clickable { onPhotoClick.invoke() },
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initialLetter.uppercase(Locale.ROOT),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
    }
}