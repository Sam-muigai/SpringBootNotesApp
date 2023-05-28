package com.sam.springbootnotesapp.feature_authentication.presentation.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.springbootnotesapp.R
import com.sam.springbootnotesapp.feature_authentication.presentation.components.LoginButton
import com.sam.springbootnotesapp.feature_authentication.presentation.components.RegisterButton
import com.sam.springbootnotesapp.feature_authentication.presentation.components.TopBar
import com.sam.springbootnotesapp.ui.theme.lato
import com.sam.springbootnotesapp.ui.theme.lato_light
import com.sam.springbootnotesapp.util.Routes
import com.sam.springbootnotesapp.util.UiEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WelcomeScreen(
    navigate:(String) ->Unit
) {
    WelcomeScreenContent(navigate = navigate)
}

@Composable
fun WelcomeScreenContent(
    navigate: (String) -> Unit
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
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.login_request),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = lato_light,
                    fontWeight = FontWeight.ExtraLight
                )
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                LoginButton(
                    text = stringResource(id = R.string.login),
                    onClick = {navigate(Routes.LOGIN)}
                )
                Spacer(modifier = Modifier.height(10.dp))
                RegisterButton(
                    onClick = { navigate(Routes.SIGN_UP) }
                ) {
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