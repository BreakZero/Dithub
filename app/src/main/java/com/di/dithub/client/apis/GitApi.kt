package com.di.dithub.client.apis

import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {
    @GET("/users/{username}/repos")
    suspend fun repos(@Path("username") username: String): Any
}