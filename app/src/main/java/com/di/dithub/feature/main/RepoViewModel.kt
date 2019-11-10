package com.di.dithub.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.client.apis.GitApi
import com.di.dithub.model.response.RepoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(private val gitApi: GitApi) : BaseViewModel() {
    private val _reposResult = BaseLiveData<List<RepoInfo>>(listOf())
    val repoResult: LiveData<List<RepoInfo>>
        get() = _reposResult

    fun fetchRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            request(
                call = {
                    val result = gitApi.repos("BreakZero", 1)
                    _reposResult.update(result)
                },
                onError = {

                })
        }
    }
}