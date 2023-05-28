package com.sam.springbootnotesapp.feature_notes.data.remote.repository

import com.sam.springbootnotesapp.feature_notes.data.remote.NotesApi
import com.sam.springbootnotesapp.feature_notes.data.remote.dto.NotesDto
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val api:NotesApi
):NotesRepository {
    override fun getAllNotes(email:String): Flow<DataState<List<Notes>>> = flow {
        try {
            emit(DataState.Loading)
            while (true){
                val notes = api.getNotesByEmail(email).map { it.toNotes() }
                emit(DataState.Success(notes))
                delay(1000L)
            }
        }catch (e:IOException){
            emit(DataState.Error("No internet connection!!"))
        }catch (e:Exception) {
            emit(DataState.Error(e.localizedMessage ?: "Something went wrong try again later"))
        }
    }

    override suspend fun getNoteById(id: Int):Notes {
        return api.getNoteById(id).toNotes()
    }

    override fun deleteById(id: Int): Flow<DataState<String>> = flow{
        try {
            emit(DataState.Loading)
            api.deleteNoteById(id)
            emit(DataState.Success("Deleted successfully"))
        }catch (e:Exception){
            emit(DataState.Error(e.message ?: ""))
        }
    }

    override suspend fun addNote(notes: Notes): Flow<DataState<String>> = flow{
        try {
            emit(DataState.Loading)
            api.addNotes(notes.toNotesDto())
            emit(DataState.Success("Successfully saved the notes."))
        }catch (e:IOException){
            e.printStackTrace()
            emit(DataState.Error("No internet connection!"))
        }
        catch (e:Exception){
            e.printStackTrace()
            emit(DataState.Error("Unknown error occurred!!"))
        }
    }

    override fun searchTerm(email: String, searchTerm: String): Flow<DataState<List<Notes>>> = flow {
        try {
            emit(DataState.Loading)
            while (true){
                val notes = api.searchNotes(email, searchTerm).map {
                    it.toNotes()
                }
                emit(DataState.Success(notes))
                delay(1000L)
            }
        }catch (e:IOException){
            emit(DataState.Error(e.localizedMessage ?: "No internet connection!"))
        }catch (e:HttpException){
            emit(DataState.Error(e.localizedMessage ?: "Server error occurred!"))
        }catch (e:Exception){
            emit(DataState.Error("Unknown error occurred!"))
        }
    }

}