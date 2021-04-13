package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.data.repository.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { BoardRepository(get()) }
}