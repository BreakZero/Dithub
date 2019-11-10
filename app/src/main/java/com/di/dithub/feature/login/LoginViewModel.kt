package com.di.dithub.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.client.AuthRetrofit
import com.di.dithub.client.apis.AuthApis
import com.di.dithub.comm.GithubConfig
import com.di.dithub.model.request.CreateAuthorization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val authRetrofit: AuthRetrofit) : BaseViewModel() {
    private val _signInStatus = BaseLiveData(SignInStatus.DEFAULT)
    val signInStatus: LiveData<SignInStatus>
        get() = _signInStatus

    fun signIn(username: String, password: String) {
        val createAuthorization = CreateAuthorization(
            clientId = GithubConfig.CLIENT_ID,
            clientSecret = GithubConfig.CLIENT_SECRET,
            note = GithubConfig.NOTE
        )

        authRetrofit.setUserInfo(username, password)
        val authApis = authRetrofit.retrofit.create(AuthApis::class.java)

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("=====", " *** ${this.coroutineContext}")
            request(call = {
                val authResp = authApis.auth(createAuthorization)
                this.launch(Dispatchers.Main) {
                    this.launch(Dispatchers.IO) {
                        authApis.getUserInfo(authResp.token)
                        _signInStatus.update(SignInStatus.SUCCESS)
                    }
                }
            }, onError = {
                this.launch(Dispatchers.Main) {
                    _signInStatus.update(SignInStatus.FAILURE)
                    Log.e("=====", "${Thread.currentThread().name}, ${it.message}")
                }
            })
        }
    }
}

enum class SignInStatus {
    DEFAULT, SUCCESS, FAILURE
}