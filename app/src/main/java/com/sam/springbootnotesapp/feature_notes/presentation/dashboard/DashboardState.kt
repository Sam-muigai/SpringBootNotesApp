package com.sam.springbootnotesapp.feature_notes.presentation.dashboard

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes

data class DashboardState(
    val data:List<Notes> = emptyList(),
    val loading:Boolean = false,
    val sortOrderShow:Boolean = false,
    val emptyMessage:String = ""
)