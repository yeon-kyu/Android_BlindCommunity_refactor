package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.BoardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardService {
    @GET("/search/free_post?")
    suspend fun getFreeBoard(@Query("cnt")cnt:Int): Response<BoardResponse>

    @GET("/search/info_post")
    suspend fun getInfoBoard(@Query("cnt")cnt: Int): Response<BoardResponse>

    @GET("/search/employee_post")
    suspend fun getEmployeeBoard(@Query("cnt")cnt: Int): Response<BoardResponse>

    @GET("/find_trace")
    suspend fun getMyBoard(@Query("user_id")id: String): Response<Any>

    @GET("/search/my_post")
    suspend fun getMyBoard2(@Query("user_id")id: String): Response<BoardResponse>

    @GET("/write_free?")
    suspend fun writeFreePost(
        @Query("post_id") postId: String,
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("user_id") userId: String
    ): Response<Any>

    @GET("/write_info?")
    suspend fun writeInfoPost(
        @Query("post_id") postId: String,
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("user_id") userId: String
    ): Response<Any>

    @GET("/write_employ")
    suspend fun writeEmployPost(
        @Query("post_id") postId: String,
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("user_id") userId: String
    ): Response<Any>
}