package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.AuthService
import com.yeonkyu.blindcommunity2.data.entities.AuthResponse
import com.yeonkyu.blindcommunity2.data.entities.LoginInfo
import com.yeonkyu.blindcommunity2.data.entities.UserInfo

class AuthRepository(private val authService: AuthService) : BaseRepository() {
    suspend fun login(loginInfo:LoginInfo): AuthResponse =
        apiRequest { authService.login(loginInfo) }

    suspend fun signup(userInfo:UserInfo): AuthResponse =
            apiRequest { authService.signup(userInfo) }
}