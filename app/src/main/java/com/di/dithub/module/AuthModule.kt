package com.di.dithub.module

import com.di.dithub.client.AuthRetrofit
import com.di.dithub.feature.login.LoginFragment
import com.di.dithub.feature.login.LoginViewModel
import com.di.dithub.repo.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthModule {
    val authModule = module(override = true) {
        single {
            AuthRetrofit()
        }
        factory {
            UserRepository(
                androidContext(),
                get()
            )
        }
        scope(named<LoginFragment>()) {
            viewModel {
                LoginViewModel(get())
            }
        }
    }
}