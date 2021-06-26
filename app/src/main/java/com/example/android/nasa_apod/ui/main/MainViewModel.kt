package com.example.android.nasa_apod.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.nasa_apod.di.key.ApiKey
import com.example.android.nasa_apod.domain.repository.ApodRepository
import com.example.android.nasa_apod.domain.util.*
import com.example.android.nasa_apod.model.ApodPost
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {

    @ApiKey
    @Inject
    lateinit var apiKey: String

    @Inject
    lateinit var gson: Gson

    private val errorEventChannel = Channel<Event>()
    val errorEvents = errorEventChannel.receiveAsFlow()

    private val refreshChannel = Channel<Refresh>()
    val refreshEvent = refreshChannel.receiveAsFlow()

    val dateUpdateChannel = Channel<ApodPost>()
    val dateEvent = dateUpdateChannel.receiveAsFlow()

    val lists = refreshEvent
        .flatMapLatest { refresh ->
            repository.getLatestApods(
                isRefresh = refresh == Refresh.FORCE,
                param = currentDate().toCustomMap(gson),
                onSuccess = {},
                onError = { throwable ->
                    throwable.printStackTrace()
                    viewModelScope.launch {
                        errorEventChannel.send(Event.ShowErrorMessage(throwable))
                    }
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun loadData() {
        if (lists.value !is Resource.Loading) {
            viewModelScope.launch { refreshChannel.send(Refresh.NORMAL) }
        }
    }

    fun refreshData() {
        if (lists.value !is Resource.Loading) {
            viewModelScope.launch {
                refreshChannel.send(Refresh.FORCE)
            }
        }
    }

    private fun currentDate(): ApodPost =
        ApodPost(apiKey = apiKey, endDate = getCurrentDate, startDate = getPastDate)
}