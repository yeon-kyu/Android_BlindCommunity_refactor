package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/search_free_content?")
    suspend fun getFreePost(@Query("post_id")postId:String): Response<Any>

    @GET("search_info_content?")
    suspend fun getInfoPost(@Query("post_id")postId:String): Response<Any>

    @GET("/search_employ_content?")
    suspend fun getEmployPost(@Query("post_id")postId:String): Response<Any>
}