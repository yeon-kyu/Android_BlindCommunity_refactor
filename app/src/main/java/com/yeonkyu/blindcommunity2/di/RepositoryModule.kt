package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.data.repository.SplashRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SplashRepository(get()) }
}