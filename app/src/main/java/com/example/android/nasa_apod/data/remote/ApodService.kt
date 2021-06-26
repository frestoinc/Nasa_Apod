package com.example.android.nasa_apod.data.remote

import com.example.android.nasa_apod.model.ApodEntity
import retrofit2.http.GET

interface ApodService {

    @GET("planetary/apod")
    suspend fun getApodList() : List<ApodEntity>
}