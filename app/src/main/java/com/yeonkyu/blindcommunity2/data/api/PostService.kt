package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.*
import retrofit2.Response
import retrofit2.http.*

interface PostService {
    @GET("/search/content/free-post")
    suspend fun getFreePostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/info-post")
    suspend fun getInfoPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/employ-post")
    suspend fun getEmployPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/comment?")
    suspend fun getComment(@Query("post_id")postId: String): Response<CommentResponse>

    @POST("/write/comment")
    suspend fun registerComment(@Body commentInfo: CommentInfo): Response<PostResponse>

    @GET("/users/check-writer")
    suspend fun isPostWriter(
            @Query("post_id")postId: String,
            @Query("user_id")userId: String
    ): Response<PostResponse>

    @PATCH("/delete/board")
    suspend fun deletePost(
            @Body boardInfo: BoardInfo
    ): Response<PostResponse>

    @PATCH("/delete/comment")
    suspend fun deleteComment(@Body commentInfo:CommentInfo): Response<CommentResponse>
}