package com.di.dithub.repo

import android.content.Context
import androidx.core.content.edit
import androidx.room.Room
import com.di.dithub.base.BaseDataSource
import com.di.dithub.client.AuthRetrofit
import com.di.dithub.client.apis.AuthApis
import com.di.dithub.comm.Constant
import com.di.dithub.comm.GithubConfig
import com.di.dithub.model.db.DithubDatabase
import com.di.dithub.model.request.CreateAuthorization
import com.di.dithub.model.response.UserInfo
import java.lang.Exception

class UserDataSource(
    private val context: Context,
    private val authRetrofit: AuthRetrofit
) : BaseDataSource() {
    private var apis: AuthApis? = null
    private val database: DithubDatabase = Room.databaseBuilder(
        context.applicationContext,
        DithubDatabase::class.java,
        "dithub_db"
    ).build()

    fun setUserInfo(username: String, password: String) {
        authRetrofit.setUserInfo(username, password)
        apis = authRetrofit.retrofit.create(AuthApis::class.java)
    }

    suspend fun authToken(onSuccess: () -> Unit, onError: (error: Exception) -> Unit) {
        val createAuthorization = CreateAuthorization(
            clientId = GithubConfig.CLIENT_ID,
            clientSecret = GithubConfig.CLIENT_SECRET,
            note = GithubConfig.NOTE
        )
        request(call = {
            val authResult = apis?.auth(createAuthorization)
            val token = authResult?.token ?: ""
            context.getSharedPreferences(Constant.AccountConst.SP_ACCOUNT, Context.MODE_PRIVATE)
                .edit {
                    putString(Constant.AccountConst.KEY_TOKEN, token).apply()
                }
            val userInfo = apis?.getUserInfo(accessToken = token)
            userInfo?.run {
                database.runInTransaction {
                    database.userDao().updateUser(this)
                }
            }
            onSuccess.invoke()
        }, onError = { onError.invoke(it) })
    }

    suspend fun currUser(): UserInfo? {
        return database.userDao().currUser()
    }
}