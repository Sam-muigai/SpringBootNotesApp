package com.sam.springbootnotesapp.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthenticationImpl
import com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication.GoogleAuthClient
import com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication.GoogleAuthClientImpl
import com.sam.springbootnotesapp.feature_notes.data.remote.NotesApi
import com.sam.springbootnotesapp.feature_notes.data.remote.repository.NotesRepositoryImpl
import com.sam.springbootnotesapp.feature_notes.domain.repository.NotesRepository
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.AddNote
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.DeleteNoteById
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.GetAllNotes
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.GetNoteById
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.GetNotesBySearch
import com.sam.springbootnotesapp.feature_notes.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideEmailPasswordAuthentication(): EmailPasswordAuthentication {
        return EmailPasswordAuthenticationImpl();
    }

    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient {
        return GoogleAuthClientImpl(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }



    @Provides
    @Singleton
    fun provideNotesApi(): NotesApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NotesApi.BASE_URL)
            .build()
            .create(NotesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotesRepository(api: NotesApi):NotesRepository{
        return NotesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(repository: NotesRepository):UseCases{
        return UseCases(
            addNote = AddNote(repository),
            deleteNoteById = DeleteNoteById(repository),
            getAllNotes = GetAllNotes(repository),
            getNoteById = GetNoteById(repository),
            getNotesBySearch = GetNotesBySearch(repository)
        )
    }

}