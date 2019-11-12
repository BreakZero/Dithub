package com.di.dithub.model.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val key: String,
    @Ignore
    val sortKey: String,
    @Ignore
    val order: String,
    val createAt: Date
)