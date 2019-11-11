package com.di.dithub.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.model.response.UserInfo
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val userRepo: UserRepository
) : BaseViewModel() {
    private val _userInfo = BaseLiveData<UserInfo?>(null)
    val userInfo: BaseLiveData<UserInfo?>
        get() = _userInfo

    private val _moduleFlag = BaseLiveData<HomeModule?>(null)
    val moduleFlag: LiveData<HomeModule?>
        get() = _moduleFlag

    fun fetchCurrUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.update(userRepo.currUser())
        }
    }

    fun selectModule(module: HomeModule) {
        if (_moduleFlag.value != module) _moduleFlag.update(module)
    }
}

enum class HomeModule(var module: String) {
    REPOSITORIES("repos"), STARTS("starred")
}