package com.yeonkyu.blindcommunity2.data.api

import retrofit2.Response

interface SplashService {
    suspend fun login(id:String, pw:String): Response<String>
}