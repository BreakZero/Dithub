package com.di.dithub.client

import com.di.dithub.comm.Constant.SystemConst.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class GitRetrofit {
    private val builder: OkHttpClient.Builder = OkHttpClient.Builder()

    val retrofit: Retrofit
        get() = buildRetrofit()

    private fun buildRetrofit(): Retrofit {
        builder.addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}