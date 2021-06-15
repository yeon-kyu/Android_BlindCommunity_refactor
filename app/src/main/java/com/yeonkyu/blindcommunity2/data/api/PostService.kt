package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.CommentResponse
import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/search/content/free-post")
    suspend fun getFreePostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/info-post")
    suspend fun getInfoPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/employ-post")
    suspend fun getEmployPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/comment?")
    suspend fun getComment(@Query("post_id")postId: String): Response<CommentResponse>

    @GET("/write_comment")
    suspend fun registerComment(
            @Query("post_id") postId: String,
            @Query("user_id") userId: String,
            @Query("comment_content") commentContent: String,
            @Query("comment_id") commentId: String
    ): Response<Any>

    @GET("/users/check-writer")
    suspend fun isPostWriter(
            @Query("post_id")postId: String,
            @Query("user_id")userId: String
    ): Response<PostResponse>

    @GET("/delete_post")
    suspend fun deletePost(
            @Query("post_id") postId: String,
            @Query("post_type") postType: Int
    ): Response<Any>

    @GET("/delete_comment")
    suspend fun deleteComment(
            @Query("comment_id") commentId: String
    ): Response<Any>
}