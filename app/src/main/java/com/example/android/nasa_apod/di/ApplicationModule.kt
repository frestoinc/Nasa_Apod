package com.example.android.nasa_apod.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    private const val TIMEOUT = 20L
    private const val BASE_URL = "https://api.nasa.gov/"
}