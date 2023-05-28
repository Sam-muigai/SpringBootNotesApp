package com.sam.springbootnotesapp.feature_authentication.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.DetailInput
import com.sam.springbootnotesapp.feature_authentication.presentation.components.LoginButton
import com.sam.springbootnotesapp.feature_authentication.presentation.components.PasswordInput
import com.sam.springbootnotesapp.feature_authentication.presentation.components.RegisterButton
import com.sam.springbootnotesapp.feature_authentication.presentation.components.TopBar
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import com.sam.springbootnotesapp.ui.theme.lato_light
import com.sam.springbootnotesapp.util.UiEvents


@Composable
fun LoginScreen(
    onGoogleSignIn: () -> Unit,
    viewModel: LoginViewModel,
    onBackClicked: () -> Unit,
    onLoginClicked: (String) -> Unit,
    context: Context
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.Navigate -> {
                    onLoginClicked(event.route)
                }
                is UiEvents.PopBackStack -> {
                    onBackClicked()
                }
                is UiEvents.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LoginScreen(
        email = viewModel.email,
        password = viewModel.password,
        onEvent = viewModel::onEvent,
        state = state,
        onGoogleSignIn = onGoogleSignIn
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEvent: (LoginEvents) -> Unit,
    state: LoginScreenState,
    onGoogleSignIn: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 19.dp,
                    vertical = 16.dp
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            TopBar(
                onIconClick = {
                    onEvent(LoginEvents.OnBackClicked)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.login_capitalize),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraLight,
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.email_request),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = lato_light,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            DetailInput(
                text = email,
                placeHolder = stringResource(id = R.string.email_request),
                onValueChange = {
                    onEvent(LoginEvents.OnEmailValueChange(it))
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            AnimatedVisibility(visible = state.emailError.isNotEmpty()) {
                Text(
                    text = state.emailError,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = lato_light,
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.password),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = lato_light,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            PasswordInput(
                modifier = Modifier,
                text = password,
                showPassword = state.showPassword,
                onValueChange = {
                    onEvent(LoginEvents.OnPasswordChange(it))
                },
                placeHolder = stringResource(id = R.string.password),
                onEyeClicked = {
                    onEvent(LoginEvents.OnVisibleIconClicked)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            AnimatedVisibility(visible = state.passwordError.isNotEmpty()) {
                Text(
                    text = state.passwordError,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = lato_light,
                        color = Color.Red
                    )
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            if (state.loading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(35.dp),
                        strokeWidth = 1.dp
                    )
                }
            } else {
                LoginButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        onEvent(LoginEvents.OnLoginClicked)
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = stringResource(id = R.string.or),
                    style = MaterialTheme.typography.bodyMedium
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            RegisterButton(onClick = onGoogleSignIn) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = stringResource(id = R.string.google_desc)
                    )
                    Text(
                        text = stringResource(id = R.string.google_sign_in),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    SpringBootNotesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}