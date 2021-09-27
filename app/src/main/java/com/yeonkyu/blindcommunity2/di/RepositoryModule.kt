package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.data.repository.AuthRepository
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { BoardRepository(get()) }
    single { PostRepository(get(), get()) }
}