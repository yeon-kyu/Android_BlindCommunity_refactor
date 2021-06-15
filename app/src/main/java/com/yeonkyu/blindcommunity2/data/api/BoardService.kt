package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.BoardResponse
import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BoardService {
    @GET("/search/free-post?")
    suspend fun getFreeBoard(@Query("cnt")cnt:Int): Response<BoardResponse>

    @GET("/search/info-post")
    suspend fun getInfoBoard(@Query("cnt")cnt: Int): Response<BoardResponse>

    @GET("/search/employee-post")
    suspend fun getEmployeeBoard(@Query("cnt")cnt: Int): Response<BoardResponse>

    @GET("/search/my-post")
    suspend fun getMyBoard2(@Query("user_id")id: String): Response<BoardResponse>

    @POST("write/free-post")
    suspend fun writeFreePost(@Body postInfo: PostInfo): Response<PostResponse>

    @POST("/write/info-post")
    suspend fun writeInfoPost(@Body postInfo: PostInfo): Response<PostResponse>

    @POST("/write/employ-post")
    suspend fun writeEmployPost(@Body postInfo: PostInfo): Response<PostResponse>
}