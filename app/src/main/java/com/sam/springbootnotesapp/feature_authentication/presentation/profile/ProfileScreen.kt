package com.sam.springbootnotesapp.feature_authentication.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.LoginButton
import com.sam.springbootnotesapp.ui.theme.lato
import com.sam.springbootnotesapp.util.UiEvents

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    context: Context, navigate: (String) -> Unit
) {
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.uiEvents.collect {
                when (it) {
                    is UiEvents.Navigate -> {
                        navigate(it.route)
                    }

                    else -> Unit
                }
            }
        }
    )
    ProfileScreenContent(
        imageUrl = viewModel.imageUrl,
        initials = viewModel.initials,
        context = context,
        email = viewModel.email,
        onSignOutClicked = viewModel::signOut
    )
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: Uri? = null,
    context: Context,
    initials: String = "",
    email: String,
    onSignOutClicked: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                if (imageUrl != null)
                    AsyncImage(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        model = ImageRequest.Builder(context)
                            .data(imageUrl.toString()).build(),
                        contentDescription = null
                    )
                else
                    Surface(
                        modifier = Modifier
                            .size(150.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.5f
                        ),
                        content = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials.uppercase(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontSize = 48.sp
                                    )
                                )
                            }
                        }
                    )
                Text(
                    text = email,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = lato
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoginButton(
                    text = stringResource(id = R.string.log_out),
                    onClick = onSignOutClicked
                )
            }
        }
    }
}