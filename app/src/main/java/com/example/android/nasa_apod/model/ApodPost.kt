package com.example.android.nasa_apod.model

import com.google.gson.annotations.SerializedName

data class ApodPost(
    @SerializedName("api_key")
    val apiKey: String?,
    @SerializedName("start_date")
    val email: String?,
    @SerializedName("end_date")
    val userId: String?
) {
    constructor(startDate: String, endDate: String) :
            this("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo", startDate, endDate)
}