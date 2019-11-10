package com.di.dithub.model.response

import com.google.gson.annotations.SerializedName

data class RepoInfo(
    val id: String,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    val description: String,
    val url: String
)