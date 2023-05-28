package com.sam.springbootnotesapp.feature_notes.domain.use_cases

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetNoteById @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id:Int):Notes{
        return repository.getNoteById(id)
    }


}