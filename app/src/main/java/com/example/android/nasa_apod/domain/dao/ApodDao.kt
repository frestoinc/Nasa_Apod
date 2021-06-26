package com.example.android.nasa_apod.domain.dao

import androidx.room.*
import com.example.android.nasa_apod.model.ApodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEntries(list: List<ApodEntity>)

    @Query("SELECT * FROM apod ORDER BY apod.date DESC")
    fun getAll(): Flow<List<ApodEntity>>

    @Query("DELETE FROM apod")
    suspend fun deleteAll()
}