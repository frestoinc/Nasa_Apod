package com.example.android.nasa_apod.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity

@Keep
@Parcelize
@Entity(tableName = "apod")
data class ApodEntity(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("explanation")
    val explanation: String,
    @SerializedName("hdurl")
    val hdurl: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
) : Parcelable