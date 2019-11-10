package com.di.dithub.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_user")
data class UserInfo(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val nickname: String,
    @SerializedName("public_repos")
    val repoNum: Int
)