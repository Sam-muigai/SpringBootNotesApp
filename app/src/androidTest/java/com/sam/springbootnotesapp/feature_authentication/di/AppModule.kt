package com.sam.springbootnotesapp.feature_authentication.di

import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthentication
import com.sam.springbootnotesapp.feature_authentication.presentation.email_password_auth.EmailPasswordAuthenticationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [])
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideEmailPasswordAuthentication():EmailPasswordAuthentication{
        return EmailPasswordAuthenticationImpl();
    }

}