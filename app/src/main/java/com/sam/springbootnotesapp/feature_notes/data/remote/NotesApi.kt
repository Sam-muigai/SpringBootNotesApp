package com.sam.springbootnotesapp.feature_notes.data.remote

import com.sam.springbootnotesapp.feature_notes.data.remote.dto.NotesDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface NotesApi {


    @GET("notes/{email}")
    suspend fun getNotesByEmail(@Path("email") email:String): List<NotesDto>

    @GET("note/{id}")
    suspend fun getNoteById(@Path("id") id:Int):NotesDto

    @DELETE("notes/{id}")
    suspend fun deleteNoteById(@Path("id") id:Int):Response<ResponseBody>

    @POST("notes")
    suspend fun addNotes(@Body notes:NotesDto):Response<ResponseBody>

    @GET("search/{email}/{searchTerm}")
    suspend fun searchNotes(@Path("email") email: String,@Path("searchTerm") searchTerm:String):List<NotesDto>

    companion object{
        const val BASE_URL = "http://192.168.100.8:8080/v1/"
    }

}