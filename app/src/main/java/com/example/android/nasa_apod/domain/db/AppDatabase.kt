package com.example.android.nasa_apod.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.nasa_apod.domain.dao.ApodDao
import com.example.android.nasa_apod.model.ApodEntity

@Database(entities = [ApodEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodListDao(): ApodDao
}