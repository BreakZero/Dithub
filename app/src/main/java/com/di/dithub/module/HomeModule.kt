package com.di.dithub.module

import com.di.dithub.client.GitRetrofit
import com.di.dithub.client.apis.GitApi
import com.di.dithub.feature.LauncherViewModel
import com.di.dithub.feature.main.MainFragment
import com.di.dithub.feature.main.RepoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object HomeModule {
    val homeModule = module {
        single {
            GitRetrofit().retrofit.create(GitApi::class.java)
        }

        viewModel {
            LauncherViewModel(get())
        }
        scope(named<MainFragment>()) {
            viewModel {
                RepoViewModel(get(), get())
            }
        }
    }
}