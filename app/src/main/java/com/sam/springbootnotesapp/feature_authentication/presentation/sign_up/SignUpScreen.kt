package com.sam.springbootnotesapp.feature_authentication.presentation.sign_up

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.DetailInput
import com.sam.springbootnotesapp.feature_authentication.presentation.components.PasswordInput
import com.sam.springbootnotesapp.feature_authentication.presentation.components.RegisterButton
import com.sam.springbootnotesapp.feature_authentication.presentation.components.TopBar
import com.sam.springbootnotesapp.ui.theme.lato
import com.sam.springbootnotesapp.ui.theme.lato_light
import com.sam.springbootnotesapp.util.UiEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUpClicked: (String) -> Unit,
    popBackStack: () -> Unit,
    context: Context
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is UiEvents.Navigate -> {
                    onSignUpClicked(event.route)
                }

                is UiEvents.PopBackStack -> {
                    popBackStack()
                }

                is UiEvents.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    SignUpScreen(
        state = state,
        password = viewModel.password,
        email = viewModel.email,
        confirmPassword = viewModel.confirmPassword,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SignUpScreen(
    state: SignUpState,
    password: String,
    email: String,
    confirmPassword: String,
    onEvent: (SignUpEvents) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 19.dp,
                    vertical = 16.dp
                )
        ) {
            TopBar(
                onIconClick = {
                    onEvent(SignUpEvents.OnBackClicked)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.create_account_capitalized),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraLight
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
                placeHolder = stringResource(id = R.string.email_request),
                text = email,
                onValueChange = {
                    onEvent(SignUpEvents.OnEmailChange(it))
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            AnimatedVisibility(visible = state.emailError.isNotEmpty()) {
                Text(
                    text = state.emailError,
                    style = MaterialTheme.typography.bodySmall.copy(
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
                text = password,
                modifier = Modifier,
                placeHolder = stringResource(id = R.string.password),
                showPassword = state.showPassword,
                onValueChange = {
                    onEvent(SignUpEvents.OnPasswordChange(it))
                },
                onEyeClicked = {
                    onEvent(SignUpEvents.OnPasswordVisibleIconClicked)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            AnimatedVisibility(visible = state.passwordError.isNotEmpty()) {
                Text(
                    text = state.passwordError,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.confirm_password),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = lato_light,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            PasswordInput(
                text = confirmPassword,
                modifier = Modifier,
                placeHolder = stringResource(id = R.string.confirm_password),
                showPassword = state.showConfirmPassword,
                onValueChange = {
                    onEvent(SignUpEvents.OnConfirmPasswordChange(it))
                },
                onEyeClicked = {
                    onEvent(SignUpEvents.OnConfirmPasswordVisibleIconClicked)
                }
            )
            AnimatedVisibility(visible = state.confirmPasswordError.isNotEmpty()) {
                Text(
                    text = state.confirmPasswordError,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.error
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
                RegisterButton(onClick = { onEvent(SignUpEvents.OnSignUpClicked) }) {
                    Text(
                        text = stringResource(id = R.string.create_account),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
    }
}