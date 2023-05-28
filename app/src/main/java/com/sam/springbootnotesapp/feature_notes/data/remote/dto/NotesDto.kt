package com.sam.springbootnotesapp.feature_notes.data.remote.dto

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes

data class NotesDto(
    val category: String,
    val description: String,
    val email: String,
    val synch:Boolean,
    val id: Int? = null,
    val title: String
){
    fun toNotes():Notes{
        return Notes(
            category = category,
            description = description,
            email = email,
            id = id,
            synch = synch,
            title = title
        )
    }
}

