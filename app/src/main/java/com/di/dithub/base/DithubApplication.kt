package com.di.dithub.base

import android.app.Application
import androidx.room.Room
import com.di.dithub.client.AuthRetrofit
import com.di.dithub.client.GitRetrofit
import com.di.dithub.client.apis.GitApi
import com.di.dithub.feature.LauncherViewModel
import com.di.dithub.feature.login.LoginViewModel
import com.di.dithub.feature.main.RepoViewModel
import com.di.dithub.model.db.DithubDatabase
import com.di.dithub.repo.UserRepository
import org.koin.android.ext.koin.androidApplication
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

    factory {
        UserRepository(
            androidContext(),
            get()
        )
    }

    viewModel {
        LauncherViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RepoViewModel(get(), get())
    }
}

class DithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DithubApplication)
            modules(loginModule)
        }
    }
}