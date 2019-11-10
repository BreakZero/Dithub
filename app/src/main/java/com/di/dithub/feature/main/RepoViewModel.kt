package com.di.dithub.feature.main

import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseViewModel
import com.di.dithub.client.apis.GitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(private val gitApi: GitApi) : BaseViewModel() {
    fun fetchRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            request(
                call = {
                    gitApi.repos("BreakZero")
                },
                onError = {

                })
        }
    }
}