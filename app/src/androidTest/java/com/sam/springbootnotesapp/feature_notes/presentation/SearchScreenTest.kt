package com.sam.springbootnotesapp.feature_notes.presentation

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.presentation.search_note.SearchNoteState
import com.sam.springbootnotesapp.feature_notes.presentation.search_note.SearchScreenContent
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_cards_shows_in_loading_state(){
        composeTestRule.setContent {
            SearchScreenContent(
                searchTerm = "",
                onNoteClicked = {},
                onSearchTermChange ={} ,
                state = SearchNoteState(
                    loading = true
                ),
                focusRequester = FocusRequester.Default,
                onBackClicked = { /*TODO*/ },
                onDeleteClicked = {}
            )
        }
        composeTestRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }

    @Test
    fun data_is_displayed_correctly_when_ready(){
        composeTestRule.setContent {
            SearchScreenContent(
                searchTerm = "",
                onNoteClicked = {},
                onSearchTermChange ={} ,
                state = SearchNoteState(
                    data = listOf(
                        Notes(
                            category = "LOADING",
                            description = "This is the description of the app",
                            email = "example@gmail.com",
                            title = "Example",
                            synch = true
                        )
                    )
                ),
                focusRequester = FocusRequester.Default,
                onBackClicked = { /*TODO*/ },
                onDeleteClicked = {}
            )
        }
        composeTestRule.onNodeWithTag("data")
            .assertIsDisplayed()
    }

}