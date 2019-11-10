package com.di.dithub.client.apis

import com.di.dithub.model.response.RepoInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApi {
    @GET("/users/{username}/repos")
    suspend fun repos(
        @Path("username") username: String,
        @Query("page") currPage: Int
    ): List<RepoInfo>
}