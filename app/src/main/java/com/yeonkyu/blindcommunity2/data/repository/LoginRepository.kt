package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.AuthService

class LoginRepository(private val authService: AuthService) : BaseRepository() {

    suspend fun login(id:String, pw:String):String =
        apiRequest { authService.login(id,pw) }

}