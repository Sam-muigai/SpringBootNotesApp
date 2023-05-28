package com.sam.springbootnotesapp.feature_notes.data.local

import androidx.room.Database


@Database(
    entities = [NotesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase {
    abstract val dao:NotesDao
}