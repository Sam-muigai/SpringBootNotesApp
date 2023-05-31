package com.sam.springbootnotesapp.feature_notes.data.remote.repository

import com.sam.springbootnotesapp.feature_notes.data.remote.NotesApi
import com.sam.springbootnotesapp.feature_notes.data.remote.dto.NotesDto
import com.sam.springbootnotesapp.feature_notes.domain.model.Notes
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val api: NotesApi,
    private val delayMillis:Long = 1000
) : NotesRepository {
    override suspend fun getAllNotes(email: String): Flow<DataState<List<Notes>>> = flow {
        try {
            emit(DataState.Loading)
            while (true){
                val notes = api.getNotesByEmail(email).map { it.toNotes() }
                emit(DataState.Success(notes))
                delay(delayMillis)
            }
        } catch (e: IOException) {
            e.localizedMessage?.let {
                emit(DataState.Error(it))
            } ?: emit(DataState.Error("No internet connection"))
        } catch (e: Exception) {
            e.localizedMessage?.let {
                emit(DataState.Error(it))
            } ?: emit(DataState.Error("Unknown error occurred"))
        }


    }

    override suspend fun getNoteById(id: Int) = api.getNoteById(id).toNotes()


    override fun deleteById(id: Int): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)
            val response = api.deleteNoteById(id)
            if (response.isSuccessful) {
                emit(DataState.Success("Deleted successfully"))
            } else {
                emit(DataState.Error("Could not delete the notes."))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: ""))
        }

    }


    override suspend fun addNote(notes: Notes): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)
            val response = api.addNotes(notes = notes.toNotesDto())
            if (response.isSuccessful) {
                emit(DataState.Success("Successfully saved the notes."))
            } else {
                emit(DataState.Error("An error occurred.Please try again later"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.localizedMessage?.let { error ->
                emit(DataState.Error(error))
            }
        }
    }


    override fun searchTerm(email: String, searchTerm: String): Flow<DataState<List<Notes>>> =
        flow {
            try {
                emit(DataState.Loading)
                while (true) {
                    val notes = api.searchNotes(email, searchTerm).map {
                        it.toNotes()
                    }
                    emit(DataState.Success(notes))
                    delay(1000L)
                }
            } catch (e: IOException) {
                emit(DataState.Error(e.localizedMessage ?: "No internet connection!"))
            } catch (e: HttpException) {
                emit(DataState.Error(e.localizedMessage ?: "Server error occurred!"))
            } catch (e: Exception) {
                emit(DataState.Error("Unknown error occurred!"))
            }
        }

}