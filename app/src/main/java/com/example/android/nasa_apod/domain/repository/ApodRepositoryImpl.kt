package com.example.android.nasa_apod.domain.repository

import com.example.android.nasa_apod.api.ApodService
import com.example.android.nasa_apod.domain.dao.ApodDao
import com.example.android.nasa_apod.domain.util.Resource
import com.example.android.nasa_apod.domain.util.networkBoundResource
import com.example.android.nasa_apod.model.ApodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val appDao: ApodDao,
    private val apodService: ApodService
) :
    ApodRepository {

    override fun getLatestApods(
        isRefresh: Boolean,
        param: Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodEntity>>> =
        networkBoundResource(
            loadFromDb = {
                appDao.getAll()
            },
            createCall = {
                apodService.getApodList(param)
            },
            saveToDb = { entities ->
                appDao.deleteAll()
                appDao.saveEntries(entities)

            },
            shouldFetch = {
                isRefresh
            },
            onCallSuccess = onSuccess,
            onCallFailed = onError
        )
}