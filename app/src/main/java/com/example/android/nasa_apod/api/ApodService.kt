package com.example.android.nasa_apod.api

import com.example.android.nasa_apod.model.ApodEntity
import retrofit2.http.GET

interface ApodService {

    @GET("planetary/apod")
    suspend fun getApodList(): ApodEntity
}