package com.di.dithub.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepository
) : BaseViewModel() {

    private val _signInResult: BaseLiveData<SignInResult> = BaseLiveData(SignInResult.DEFAULT)
    val signInResult: LiveData<SignInResult>
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
        val DEFAULT = SignInResult()
        val SUCCESS = SignInResult(0)
    }
}