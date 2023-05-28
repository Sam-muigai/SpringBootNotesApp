package com.sam.springbootnotesapp.feature_notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val title:String,
    val description:String,
    val email:String,
    val category:String,
    val synchronized: Boolean
)
