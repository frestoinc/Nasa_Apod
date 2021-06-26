package com.example.android.nasa_apod.api

import com.example.android.nasa_apod.model.ApodEntityList
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApodService {

    companion object {
        const val BASE_URL = "https://api.nasa.gov/"
    }

    @GET("planetary/apod")
    suspend fun getApodList(@QueryMap params : Map<String, String>): ApodEntityList
}