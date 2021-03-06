package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.ui.account.AccountViewModel
import com.yeonkyu.blindcommunity2.ui.board.BoardViewModel
import com.yeonkyu.blindcommunity2.ui.favorite.FavoriteViewModel
import com.yeonkyu.blindcommunity2.ui.login.LoginViewModel
import com.yeonkyu.blindcommunity2.ui.post.PostViewModel
import com.yeonkyu.blindcommunity2.ui.signup.SignupViewModel
import com.yeonkyu.blindcommunity2.ui.write.WriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { BoardViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { PostViewModel(get(), get()) }
    viewModel { WriteViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}