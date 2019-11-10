package com.di.dithub.feature.main

import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.feature.login.SignInStatus

class MainViewModel : BaseViewModel() {
    private val _signInStatus = BaseLiveData(SignInStatus.DEFAULT)
    val signInStatus: BaseLiveData<SignInStatus>
        get() = _signInStatus
}