package com.di.dithub.module

import com.di.dithub.client.AuthRetrofit
import com.di.dithub.feature.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module

class LoginModule: KoinComponent {
    val loginModule: Module = module {
        single {
            AuthRetrofit()
        }
    }
}