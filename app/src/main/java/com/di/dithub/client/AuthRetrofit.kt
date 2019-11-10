package com.di.dithub.client

import android.util.Base64
import com.di.dithub.comm.Constant.SystemConst.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRetrofit {
    private var username: String? = null
    private var password: String? = null
    private val builder: OkHttpClient.Builder = OkHttpClient.Builder()

    val retrofit: Retrofit
        get() = buildRetrofit()

    fun setUserInfo(username: String, password: String) {
        this.username = username
        this.password = password
    }

    private fun buildRetrofit(): Retrofit {
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val userCredentials = "$username:$password"
                val basicAuth =
                    "Basic ${String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT))}"
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", basicAuth.trim { it <= ' ' })
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}