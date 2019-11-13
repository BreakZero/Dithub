package com.di.dithub.feature.login

import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.repo.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserDataSource
) : BaseViewModel() {

    private val _signInResult: BaseLiveData<SignInResult> = BaseLiveData()
    val signInResult: BaseLiveData<SignInResult>
        get() = _signInResult

    fun signIn(username: String, password: String) {
        userRepo.setUserInfo(username, password)

        viewModelScope.launch(Dispatchers.IO) {
            userRepo.authToken(
                onSuccess = {
                    _signInResult.update(SignInResult.SUCCESS)
                },
                onError = {
                    _signInResult.update(SignInResult(1, it.message))
                    it.printStackTrace()
                }
            )
        }
    }
}

data class SignInResult(
    val code: Int = -1,
    val error: String? = null
) {
    companion object {
        val SUCCESS = SignInResult(0)
    }
}