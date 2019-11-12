package com.di.dithub.feature

import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.base.SingleLiveEvent
import com.di.dithub.model.response.UserInfo
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val userRepo: UserRepository
) : BaseViewModel() {
    private val _userInfo = BaseLiveData<UserInfo>()
    val userInfo: BaseLiveData<UserInfo>
        get() = _userInfo

    private val _moduleFlag = SingleLiveEvent<HomeModule?>()
    val moduleFlag: SingleLiveEvent<HomeModule?>
        get() = _moduleFlag

    fun fetchCurrUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.update(userRepo.currUser())
        }
    }

    fun selectModule(module: HomeModule) {
        if (_moduleFlag.value != module) {
            _moduleFlag.value = module
        }
    }
}

enum class HomeModule(var module: String) {
    REPOSITORIES("repos"), STARS("starred")
}