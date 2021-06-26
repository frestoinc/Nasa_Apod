package com.example.android.nasa_apod.di

import com.example.android.nasa_apod.BuildConfig
import com.example.android.nasa_apod.api.ApodService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val TIMEOUT = 20L
    private const val BASE_URL = "https://api.nasa.gov/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(url: String, client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(url)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(client)
        }.build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().apply {
        setLenient()
    }.create()

    @Provides
    @Singleton
    fun provideApodService(client: OkHttpClient, gson: Gson): ApodService =
        provideRetrofit(BASE_URL, client, gson).create(ApodService::class.java)
}