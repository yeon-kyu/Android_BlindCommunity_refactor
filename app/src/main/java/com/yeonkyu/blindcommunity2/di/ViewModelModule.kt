package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.ui.account.AccountViewModel
import com.yeonkyu.blindcommunity2.ui.board.BoardViewModel
import com.yeonkyu.blindcommunity2.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { BoardViewModel(get()) }
    viewModel { AccountViewModel(get()) }
}