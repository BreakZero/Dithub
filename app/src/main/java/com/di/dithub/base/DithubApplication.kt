package com.di.dithub.base

import android.app.Application
import com.di.dithub.BuildConfig
import com.di.dithub.module.AuthModule
import com.di.dithub.module.HomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class DithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DithubApplication)
            modules(listOf(AuthModule.authModule, HomeModule.homeModule))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}