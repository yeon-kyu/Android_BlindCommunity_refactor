package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.data.repository.LoginRepository
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { BoardRepository(get()) }
    single { PostRepository(get()) }
}