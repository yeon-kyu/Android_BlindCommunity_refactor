package com.yeonkyu.blindcommunity2

import android.app.Application
import com.yeonkyu.blindcommunity2.di.networkModule
import com.yeonkyu.blindcommunity2.di.repositoryModule
import com.yeonkyu.blindcommunity2.di.roomDBModule
import com.yeonkyu.blindcommunity2.di.viewModelModule
import com.yeonkyu.blindcommunity2.utils.PreferenceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationClass : Application() {

    companion object{
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)

        startKoin{
            androidLogger()
            androidContext(this@ApplicationClass)
            modules(listOf(viewModelModule, networkModule, repositoryModule, roomDBModule))
        }
    }
}