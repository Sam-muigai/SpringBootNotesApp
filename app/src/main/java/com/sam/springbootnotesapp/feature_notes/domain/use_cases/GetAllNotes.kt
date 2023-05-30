package com.sam.springbootnotesapp.feature_notes.domain.use_cases

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.convertToCategoryItem
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.Category.Companion.toCategory
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException

import javax.inject.Inject

class GetAllNotes @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(email:String): Flow<DataState<List<Notes>>> = repository.getAllNotes(email)
}