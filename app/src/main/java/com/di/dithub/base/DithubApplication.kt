package com.di.dithub.base

import android.app.Application
import com.di.dithub.client.AuthRetrofit
import com.di.dithub.client.GitRetrofit
import com.di.dithub.client.apis.GitApi
import com.di.dithub.feature.login.LoginViewModel
import com.di.dithub.feature.main.MainViewModel
import com.di.dithub.feature.main.RepoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModule: Module = module {
    single {
        AuthRetrofit()
    }
    single {
        GitRetrofit().retrofit.create(GitApi::class.java)
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        MainViewModel()
    }

    viewModel {
        RepoViewModel(get())
    }
}

class DithubApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DithubApplication)
            modules(loginModule)
        }
    }
}