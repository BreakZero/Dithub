package com.di.dithub.model.request

import com.google.gson.annotations.SerializedName

data class CreateAuthorization(
    val note: String,
    @SerializedName("note_url")
    val noteUrl: String? = null,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    val scopes: List<String> = listOf()
)
