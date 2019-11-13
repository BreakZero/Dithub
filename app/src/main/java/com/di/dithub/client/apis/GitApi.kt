package com.di.dithub.client.apis

import com.di.dithub.model.response.RepoInfo
import com.di.dithub.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApi {
    @GET("/users/{username}/{module}")
    suspend fun repos(
        @Path("username") username: String,
        @Path("module") module: String,
        @Query("page") currPage: Int
    ): List<RepoInfo>

    @GET("/search/repositories")
    suspend fun searchRepos(
        @Query("q") key: String,
        @Query("sort") sort: String? = "updated",
        @Query("order") order: String? = "desc",
        @Query("page") currPage: Int
    ): SearchResponse
}