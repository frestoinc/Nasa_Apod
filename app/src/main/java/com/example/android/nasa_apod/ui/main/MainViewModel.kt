package com.example.android.nasa_apod.ui.main

import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.android.nasa_apod.domain.repository.ApodRepository
import com.example.android.nasa_apod.model.ApodEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {

    private val _listFlow: Flow<List<ApodEntity>> = flow {
        emit(emptyList())
    }


    val refreshing: ObservableBoolean = ObservableBoolean(false)

    val lists = _listFlow.asLiveData()

    fun refreshData() {
        refreshing.set(true)
        Timber.e("refreshing")
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            refreshing.set(false)
            Timber.e("stop refreshing")
        }, 5000)
    }
}