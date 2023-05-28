package com.sam.springbootnotesapp.feature_notes.domain.repository

import com.sam.springbootnotesapp.feature_notes.data.remote.dto.NotesDto
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getAllNotes(email:String):Flow<DataState<List<Notes>>>

    suspend fun getNoteById(id:Int):Notes

    fun deleteById(id: Int):Flow<DataState<String>>

    suspend fun addNote(notes: Notes):Flow<DataState<String>>

    fun searchTerm(email: String,searchTerm:String):Flow<DataState<List<Notes>>>

}