package com.sam.springbootnotesapp.feature_notes.presentation.search_note

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes

data class SearchNoteState(
    val loading:Boolean = false,
    val data:List<Notes> = emptyList(),
)
