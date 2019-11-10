package com.di.dithub.feature

import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.feature.login.SignInStatus
import com.di.dithub.model.response.UserInfo
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val userRepo: UserRepository
): BaseViewModel() {
    private val _signInStatus = BaseLiveData(SignInStatus.DEFAULT)
    val signInStatus: BaseLiveData<SignInStatus>
        get() = _signInStatus

    private val _userInfo = BaseLiveData<UserInfo?>(null)
    val userInfo: BaseLiveData<UserInfo?>
        get() = _userInfo

    fun fetchCurrUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.update(userRepo.currUser())
        }
    }
}