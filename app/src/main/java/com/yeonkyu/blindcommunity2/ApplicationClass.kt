package com.yeonkyu.blindcommunity2

import android.app.Application
import com.yeonkyu.blindcommunity2.di.networkModule
import com.yeonkyu.blindcommunity2.di.repositoryModule
import com.yeonkyu.blindcommunity2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@ApplicationClass)
            modules(viewModelModule, networkModule, repositoryModule)

        }
    }
}