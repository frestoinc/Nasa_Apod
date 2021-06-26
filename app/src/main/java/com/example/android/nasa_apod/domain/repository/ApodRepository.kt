package com.example.android.nasa_apod.domain.repository

import com.example.android.nasa_apod.domain.util.Resource
import com.example.android.nasa_apod.model.ApodEntity
import kotlinx.coroutines.flow.Flow

interface ApodRepository {
    fun getLatestApods(
        isRefresh : Boolean,
        param : Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodEntity>>>
}