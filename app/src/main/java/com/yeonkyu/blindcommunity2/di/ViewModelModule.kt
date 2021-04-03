package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.ui.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
}