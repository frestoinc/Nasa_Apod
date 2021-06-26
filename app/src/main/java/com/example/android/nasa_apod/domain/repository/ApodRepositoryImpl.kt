package com.example.android.nasa_apod.domain.repository

import com.example.android.nasa_apod.domain.dao.ApodDao
import com.example.android.nasa_apod.domain.db.AppDatabase
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(private val appDao: ApodDao) :
    ApodRepository {
}