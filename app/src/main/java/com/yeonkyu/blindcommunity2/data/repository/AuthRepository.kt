package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.AuthService
import com.yeonkyu.blindcommunity2.data.entities.AuthResponse
import com.yeonkyu.blindcommunity2.data.entities.LoginInfo

class AuthRepository(private val authService: AuthService) : BaseRepository() {

    suspend fun login(loginInfo:LoginInfo): AuthResponse =
        apiRequest { authService.login(loginInfo) }

    suspend fun signup(id:String, pw:String, nickname:String, age:String): String =
            apiRequest { authService.signup(id,pw,nickname,age) }
}