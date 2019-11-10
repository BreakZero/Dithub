package com.di.dithub.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {
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