package com.example.android.nasa_apod.data.local.dao

import androidx.room.*
import com.example.android.nasa_apod.model.ApodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEntries(list: List<ApodEntity>)

    @Query("SELECT * FROM apod")
    fun getAll(): Flow<List<Entity>>

    @Query("DELETE FROM apod")
    suspend fun deleteAll()
}