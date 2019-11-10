package com.di.dithub.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.di.dithub.model.db.dao.UserDao
import com.di.dithub.model.response.UserInfo

@Database(entities = [UserInfo::class], version = 1)
abstract class DithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}