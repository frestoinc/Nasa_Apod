package com.example.android.nasa_apod.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Parcelize
@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey(autoGenerate = true) //1
    var id: Int = 0,
    @SerializedName("copyright")  //2
    val copyright: String?,
    @SerializedName("date") //3
    val date: String?,
    @SerializedName("explanation") //4
    val explanation: String?,
    @SerializedName("hdurl") //5
    val hdurl: String?,
    @SerializedName("media_type") //6
    val mediaType: String?,
    @SerializedName("service_version") //7
    val serviceVersion: String?,
    @SerializedName("title") //8
    val title: String?,
    @SerializedName("url") //9
    val url: String?
) : Parcelable {}