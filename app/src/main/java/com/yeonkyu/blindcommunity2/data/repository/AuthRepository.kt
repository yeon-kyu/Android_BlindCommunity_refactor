package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.AuthService

class AuthRepository(private val authService: AuthService) : BaseRepository() {

    suspend fun login(id:String, pw:String): String =
        apiRequest { authService.login(id,pw) }

    suspend fun signup(id:String, pw:String, nickname:String, age:String): String =
            apiRequest { authService.signup(id,pw,nickname,age) }
}