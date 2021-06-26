package com.example.android.nasa_apod.di

import com.example.android.nasa_apod.ui.main.MainAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @Provides
    fun provideMainAdapter() : MainAdapter = MainAdapter()
}