package com.di.dithub.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.model.response.UserInfo
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepository
) : BaseViewModel() {
    private val _signInStatus = BaseLiveData(SignInStatus.DEFAULT)
    val signInStatus: LiveData<SignInStatus>
        get() = _signInStatus

    private val _userInfo = BaseLiveData<UserInfo?>(null)
    val userInfo: LiveData<UserInfo?>
        get() = _userInfo

    fun signIn(username: String, password: String) {

        userRepo.setUserInfo(username, password)

        viewModelScope.launch(Dispatchers.IO) {
            userRepo.authToken(
                onSuccess = {
                    _signInStatus.update(SignInStatus.SUCCESS)
                },
                onError = {
                    _signInStatus.update(SignInStatus.FAILURE)
                }
            )
        }
    }
}

enum class SignInStatus {
    DEFAULT, SUCCESS, FAILURE
}