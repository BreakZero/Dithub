package com.di.dithub.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    suspend fun <T> request(
        call: suspend CoroutineScope.() -> T,
        onError: suspend CoroutineScope.(error: Exception) -> Unit
    ): T? {
        return withContext(Dispatchers.IO) {
            try {
                call()
            } catch (error: Exception) {
                error.printStackTrace()
                onError(error)
                null
            }
        }
    }
}