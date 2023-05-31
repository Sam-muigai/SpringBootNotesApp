package com.sam.springbootnotesapp.feature_authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO: Write the test for all possible screen states


    @Test
    fun email_error_message_displays_if_it_is_not_empty() {
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                LoginScreen(
                    email = "",
                    password = "",
                    onBackClicked = { /*TODO*/ },
                    onEvent = {},
                    state = LoginScreenState(
                        emailError = "Email not valid"
                    ),
                    onGoogleSignIn = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Email not valid")
            .assertIsDisplayed()
    }

    @Test
    fun password_error_message_displays_if_it_is_not_empty() {
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                LoginScreen(
                    email = "",
                    password = "",
                    onBackClicked = { /*TODO*/ },
                    onEvent = {},
                    state = LoginScreenState(
                        passwordError = "Password not valid",
                    ),
                    onGoogleSignIn = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Password not valid")
            .assertIsDisplayed()
    }


    @Test
    fun loading_spinner_shows_in_loading_state() {
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                LoginScreen(
                    email = "",
                    password = "",
                    onBackClicked = { /*TODO*/ },
                    onEvent = {},
                    state = LoginScreenState(
                        loading = true
                    ),
                    onGoogleSignIn = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }

    @Test
    fun password_is_visible_if_show_password_is_true(){
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                LoginScreen(
                    email = "",
                    password = "12345678",
                    onBackClicked = { /*TODO*/ },
                    onEvent = {},
                    state = LoginScreenState(
                        showPassword = true
                    ),
                    onGoogleSignIn = {}
                )
            }
        }

        composeTestRule.onNodeWithText("12345678")
            .assertIsDisplayed()
    }

    @Test
    fun password_is_not_visible_if_show_password_is_false(){
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                LoginScreen(
                    email = "",
                    password = "12345678",
                    onBackClicked = { /*TODO*/ },
                    onEvent = {},
                    state = LoginScreenState(
                        showPassword = false
                    ),
                    onGoogleSignIn = {}
                )
            }
        }

        composeTestRule.onNodeWithText("12345678")
            .assertDoesNotExist()
    }

//    @Test
//    fun show_password_icon_is_shown_when_show_icon_state_is_true(){
//        composeTestRule.setContent {
//            var password by remember{
//                mutableStateOf("")
//            }
//            SpringBootNotesAppTheme {
//                LoginScreen(
//                    email = "",
//                    password = password,
//                    onBackClicked = { /*TODO*/ },
//                    onEvent = {
//                              if (it is LoginEvents.OnPasswordChange){
//                                  password = it.value
//                              }
//                    },
//                    state = LoginScreenState(),
//                    onGoogleSignIn = {}
//                )
//            }
//        }
//
//        composeTestRule.onNodeWithTag("password_input")
//            .performTextInput("12345678")
//
//        composeTestRule.onNodeWithTag("eye_icon")
//            .assertIsDisplayed()
//    }

}