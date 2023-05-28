package com.sam.springbootnotesapp.feature_notes.domain.model

import com.sam.springbootnotesapp.feature_notes.data.remote.dto.NotesDto

data class Notes(
    val category: String,
    val description: String,
    val email: String,
    val id: Int? = null,
    val title: String,
    val synch: Boolean
){
    fun toNotesDto():NotesDto{
        return NotesDto(
            category = category,
            description = description,
            email = email,
            id = id,
            title = title,
            synch = synch
        )
    }
}