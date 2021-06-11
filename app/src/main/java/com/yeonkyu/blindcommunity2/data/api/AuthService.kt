package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.AuthResponse
import com.yeonkyu.blindcommunity2.data.entities.LoginInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("users/login")
    suspend fun login(@Body loginInfo: LoginInfo): Response<AuthResponse>

    @GET("sign_up")
    suspend fun signup(
            @Query("id")id: String,
            @Query("pw")pw: String,
            @Query("nickname")nickname: String,
            @Query("age")age: String
    ) : Response<String>
}