package com.rcdeivys.clevertest.app

import android.app.Application
import com.rcdeivys.clevertest.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CleverApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CleverApp)
            modules(appModule)
        }
    }
}