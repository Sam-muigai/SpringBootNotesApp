package com.sam.springbootnotesapp.feature_notes.domain.use_cases

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetNotesBySearch @Inject constructor(
    private val repository: NotesRepository
){
    operator fun invoke(email:String, searchTerm:String):Flow<DataState<List<Notes>>> =
        repository.searchTerm(email, searchTerm)
}