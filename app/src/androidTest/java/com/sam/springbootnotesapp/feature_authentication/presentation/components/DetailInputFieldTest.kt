package com.sam.springbootnotesapp.feature_authentication.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailInputFieldTest{
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Before
    fun setup(){
        composeTestRule.setContent {
            var input by remember {
                mutableStateOf("")
            }
            SpringBootNotesAppTheme() {

            }
        }
    }
    
    @Test
    fun when_input_entered_shown_correctly(){
        composeTestRule.onNodeWithTag("detail_input_field")
            .performTextInput("James John")

        composeTestRule.onNodeWithTag("detail_input_field")
            .assertTextEquals("James John", includeEditableText = true)
    }
    
}