package com.example.android.nasa_apod.di

import com.example.android.nasa_apod.api.ApodService
import com.example.android.nasa_apod.domain.dao.ApodDao
import com.example.android.nasa_apod.domain.repository.ApodRepository
import com.example.android.nasa_apod.domain.repository.ApodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideApodRepository(appDao: ApodDao, apodService: ApodService): ApodRepository =
        ApodRepositoryImpl(appDao, apodService)
}