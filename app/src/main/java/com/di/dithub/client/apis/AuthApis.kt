package com.di.dithub.client.apis

import com.di.dithub.model.request.CreateAuthorization
import com.di.dithub.model.response.AuthorizationResp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApis {
    @POST("/authorizations")
    suspend fun auth(@Body body: CreateAuthorization): AuthorizationResp

    @GET("/user")
    suspend fun getUserInfo(@Query("access_token") accessToken: String): Any
}