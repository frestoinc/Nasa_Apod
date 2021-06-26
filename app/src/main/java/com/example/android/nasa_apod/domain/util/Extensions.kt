package com.example.android.nasa_apod.domain.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline createCall: suspend () -> RequestType,
    crossinline saveToDb: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onCallSuccess: () -> Unit = { },
    crossinline onCallFailed: (Throwable) -> Unit = { }
) = channelFlow {
    val data = loadFromDb().first()

    if (shouldFetch(data)) {
        val loading = launch {
            loadFromDb().collect { send(Resource.Loading(it)) }
        }

        try {
            saveToDb(createCall())
            onCallSuccess()
            loading.cancel()
            loadFromDb().collect { send(Resource.Success(it)) }
        } catch (t: Throwable) {
            onCallFailed(t)
            loading.cancel()
            loadFromDb().collect { send(Resource.Error(t, it)) }
        }
    } else {
        loadFromDb().collect { send(Resource.Success(it)) }
    }
}

inline fun <reified T> T.toJsonString(): String = Gson().toJson(this)

inline fun <reified T> T.toCustomMap(): Map<String, String> = Gson().fromJson(
    toJsonString(),
    object : TypeToken<Map<String, String>>() {}.type
)

enum class Refresh {
    FORCE, NORMAL
}

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
}

fun Fragment.showSnackBarError(message: String, view: View = requireView()) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

val <T> T.exhaustive: T
    get() = this