package com.di.dithub.feature.main

import androidx.lifecycle.viewModelScope
import com.di.dithub.base.BaseLiveData
import com.di.dithub.base.BaseViewModel
import com.di.dithub.client.apis.GitApi
import com.di.dithub.model.response.RepoInfo
import com.di.dithub.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(
    private val userRepo: UserRepository,
    private val gitApi: GitApi
) : BaseViewModel() {
    private val _reposResult = BaseLiveData<List<RepoInfo>>()
    val repoResult: BaseLiveData<List<RepoInfo>>
        get() = _reposResult

    fun fetchRepos(module: String) {
        viewModelScope.launch(Dispatchers.IO) {
            request(
                call = {
                    userRepo.currUser()?.let {
                        val result = gitApi.repos(it.nickname, module, 1)
                        _reposResult.update(result)
                    }
                },
                onError = {
                    repoResult.update(listOf())
                })
        }
    }
}