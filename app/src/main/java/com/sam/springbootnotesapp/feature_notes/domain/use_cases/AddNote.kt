package com.sam.springbootnotesapp.feature_notes.domain.use_cases

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AddNote @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(notes:Notes):Flow<DataState<String>> = repository.addNote(notes)
}

