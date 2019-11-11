package com.di.dithub.model.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoInfo(
    val id: String,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    val description: String,
    @SerializedName("svn_url")
    val svnUrl: String,
    val language: String,
    @SerializedName("stargazers_count")
    val starCount: Int,
    @SerializedName("updated_at")
    val updateTime: Date
)