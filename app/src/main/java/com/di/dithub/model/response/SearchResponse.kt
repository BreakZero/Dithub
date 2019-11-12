package com.di.dithub.model.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val total: Int,
    @SerializedName("incomplete_results")
    val isCompleted: Boolean,
    val items: List<RepoInfo>
)