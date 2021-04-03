package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.SplashService
import retrofit2.Response

class LoginRepository(private val splashService: SplashService) : BaseRepository() {
    suspend fun login(id:String, pw:String): String {
        return apiRequest { splashService.login(id,pw) }
    }
}