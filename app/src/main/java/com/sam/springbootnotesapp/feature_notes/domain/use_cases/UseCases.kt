package com.sam.springbootnotesapp.feature_notes.domain.use_cases

data class UseCases(
    val addNote: AddNote,
    val deleteNoteById: DeleteNoteById,
    val getAllNotes: GetAllNotes,
    val getNoteById: GetNoteById,
    val getNotesBySearch: GetNotesBySearch
)
