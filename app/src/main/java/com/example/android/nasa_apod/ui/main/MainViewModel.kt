package com.example.android.nasa_apod.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.nasa_apod.domain.repository.ApodRepository
import com.example.android.nasa_apod.domain.util.Event
import com.example.android.nasa_apod.domain.util.Refresh
import com.example.android.nasa_apod.domain.util.Resource
import com.example.android.nasa_apod.domain.util.toCustomMap
import com.example.android.nasa_apod.model.ApodPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {


    private val errorEventChannel = Channel<Event>()
    val errorEvents = errorEventChannel.receiveAsFlow()

    private val refreshChannel = Channel<Refresh>()
    private val refreshEvent = refreshChannel.receiveAsFlow()

    val dateUpdateChannel = Channel<ApodPost>()
    val dateEvent = dateUpdateChannel.receiveAsFlow()

    val lists = refreshEvent
        .flatMapLatest { refresh ->
            repository.getLatestApods(
                isRefresh = refresh == Refresh.FORCE,
                param = ApodPost("","").toCustomMap(),
                onSuccess = {},
                onError = { throwable ->
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
            viewModelScope.launch { refreshChannel.send(Refresh.FORCE) }
        }
    }
}