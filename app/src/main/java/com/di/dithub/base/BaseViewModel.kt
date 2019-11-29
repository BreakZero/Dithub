package com.di.dithub.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    fun <T> request(
        onError: (error: Throwable) -> Unit = {},
        execute: suspend CoroutineScope.() -> T
    ) {
        viewModelScope.launch(errorHandler {
                onError.invoke(it)
            }) {
            withContext(Dispatchers.IO) {
                execute()
            }
        }
    }

    private fun errorHandler(onError: (error: Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            Timber.d(throwable)
            onError.invoke(throwable)
        }
    }
}