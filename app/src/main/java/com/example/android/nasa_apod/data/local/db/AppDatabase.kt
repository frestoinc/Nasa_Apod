package com.example.android.nasa_apod.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.nasa_apod.data.local.dao.ApodDao
import com.example.android.nasa_apod.model.ApodEntity

@Database(entities = [ApodEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodListDao(): ApodDao
}