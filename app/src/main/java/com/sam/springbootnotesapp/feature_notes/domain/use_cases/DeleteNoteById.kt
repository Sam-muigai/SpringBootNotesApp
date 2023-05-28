package com.sam.springbootnotesapp.feature_notes.domain.use_cases

import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNoteById @Inject constructor(
    private val repository: NotesRepository
) {

    operator fun invoke(id:Int):Flow<DataState<String>> = repository.deleteById(id)

}