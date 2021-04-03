package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}