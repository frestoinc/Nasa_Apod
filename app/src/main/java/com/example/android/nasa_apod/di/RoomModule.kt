package com.example.android.nasa_apod.di

import android.content.Context
import androidx.room.Room
import com.example.android.nasa_apod.data.local.dao.ApodDao
import com.example.android.nasa_apod.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase.db"
        ).allowMainThreadQueries().build()

    @Provides
    fun providePasswordListDao(database: AppDatabase): ApodDao =
        database.apodListDao()
}