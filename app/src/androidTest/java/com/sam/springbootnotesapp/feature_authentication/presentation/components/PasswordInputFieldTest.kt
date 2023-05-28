package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PasswordInputFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            var password by remember {
                mutableStateOf("")
            }
            var showPassword by remember {
                mutableStateOf(false)
            }
            SpringBootNotesAppTheme {

            }
        }
    }

    @Test
    fun when_password_is_entered_it_is_shown_correctly() {
        composeTestRule.onNodeWithTag("password_input_field")
            .performTextInput("12345678")

        composeTestRule.onNodeWithContentDescription("visible icon")
            .performClick()

        composeTestRule.onNodeWithTag("password_input_field")
            .assertTextEquals("12345678", includeEditableText = true)

    }

    @Test
    fun when_password_is_empty_icon_is_not_shown() {
        composeTestRule.onNodeWithContentDescription("visible icon")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("password_input_field")
            .performTextInput("1234567")

        composeTestRule.onNodeWithContentDescription("visible icon")
            .assertIsDisplayed()
    }

}