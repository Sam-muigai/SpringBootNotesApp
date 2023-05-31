package com.sam.springbootnotesapp.feature_authentication.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO: Write the test for all possible screen states

    @Test
    fun email_error_shows_when_error_state_is_true(){
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    emailError = "Email not valid"
                ),
                password = "",
                email = "",
                confirmPassword = "",
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithText("Email not valid")
            .assertIsDisplayed()
    }


    @Test
    fun password_error_shows_when_password_error_is_not_empty(){
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    passwordError = "Password not valid"
                ),
                password = "",
                email = "",
                confirmPassword = "",
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithText("Password not valid")
            .assertIsDisplayed()
    }

    @Test
    fun confirm_password_error_shows_when_confirm_password_error_is_not_empty(){
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    emailError = "Confirm password not valid"
                ),
                password = "",
                email = "",
                confirmPassword = "",
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithText("Confirm password not valid")
            .assertIsDisplayed()
    }

    @Test
    fun loading_spinner_is_shown_in_loading_state(){
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    loading = true
                ),
                password = "",
                email = "",
                confirmPassword = "",
                onEvent = {}
            )
        }
        composeTestRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }

}