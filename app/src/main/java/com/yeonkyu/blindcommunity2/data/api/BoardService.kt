package com.yeonkyu.blindcommunity2.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardService {
    @GET("/search_free?")
    suspend fun getFreeBoard(@Query("cnt")cnt:Int): Response<Any>

    @GET("/search_info")
    suspend fun getInfoBoard(@Query("cnt")cnt: Int): Response<Any>

    @GET("/search_employee")
    suspend fun getEmployeeBoard(@Query("cnt")cnt: Int) : Response<Any>
}