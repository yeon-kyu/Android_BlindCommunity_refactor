package com.yeonkyu.blindcommunity2.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {
    @GET("/sign_in?")
    suspend fun login(@Query("id")id:String, @Query("pw")pw:String): Response<String>
}