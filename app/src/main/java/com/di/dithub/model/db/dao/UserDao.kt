package com.di.dithub.model.db.dao

import androidx.room.*
import com.di.dithub.model.response.UserInfo

@Dao
abstract class UserDao {

    @Transaction
    open fun updateUser(userInfo: UserInfo) {
        deleteUser()
        insertUser(userInfo)
    }

    @Query("DELETE FROM tb_user")
    abstract fun deleteUser()

    @Insert
    abstract fun insertUser(userInfo: UserInfo)

    @Transaction
    open suspend fun currUser(): UserInfo? {
        return getUsers().firstOrNull()
    }

    @Query("SELECT * FROM tb_user")
    abstract fun getUsers(): List<UserInfo>
}