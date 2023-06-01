package com.sam.springbootnotesapp.feature_notes.presentation

import android.net.Uri
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sam.springbootnotesapp.MainActivity
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.presentation.dashboard.DashBoardContent
import com.sam.springbootnotesapp.feature_notes.presentation.dashboard.DashboardState
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun loading_card_shows_when_loading(){
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                DashBoardContent(
                    state = DashboardState(
                        loading = true
                    ),
                    onAddNote = {},
                    photoUrl = Uri.parse(""),
                    selectedText = "",
                    initialChar = "",
                    categories = emptyList(),
                    onPhotoClicked = { /*TODO*/ },
                    onSortClicked = { /*TODO*/ },
                    onCategorySelect = {},
                    onDeleteClicked = {},
                    onSearchClicked = { /*TODO*/ },
                    onNoteClicked = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }

    @Test
    fun data_is_displayed_when_available(){
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                DashBoardContent(
                    state = DashboardState(
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
                    onAddNote = {},
                    photoUrl = Uri.parse(""),
                    selectedText = "",
                    initialChar = "",
                    categories = emptyList(),
                    onPhotoClicked = { /*TODO*/ },
                    onSortClicked = { /*TODO*/ },
                    onCategorySelect = {},
                    onDeleteClicked = {},
                    onSearchClicked = { /*TODO*/ },
                    onNoteClicked = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("notes")
            .assertIsDisplayed()
    }

    @Test
    fun search_field_is_not_shown_when_show_category_filter_is_true(){
        composeTestRule.setContent {
            SpringBootNotesAppTheme {
                DashBoardContent(
                    state = DashboardState(
                       sortOrderShow = true
                    ),
                    onAddNote = {},
                    photoUrl = Uri.parse(""),
                    selectedText = "",
                    initialChar = "",
                    categories = emptyList(),
                    onPhotoClicked = { /*TODO*/ },
                    onSortClicked = { /*TODO*/ },
                    onCategorySelect = {},
                    onDeleteClicked = {},
                    onSearchClicked = { /*TODO*/ },
                    onNoteClicked = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("search_bar")
            .assertDoesNotExist()
    }


}