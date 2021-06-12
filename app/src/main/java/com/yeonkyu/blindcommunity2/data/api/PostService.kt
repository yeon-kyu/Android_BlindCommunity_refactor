package com.yeonkyu.blindcommunity2.data.api

import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/search_free_content?")
    suspend fun getFreePost(@Query("post_id")postId:String): Response<Any>

    @GET("/search/content/free-post")
    suspend fun getFreePostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/info-post")
    suspend fun getInfoPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("/search/content/employ-post")
    suspend fun getEmployPostContent(@Query("post_id")postId:String): Response<PostResponse>

    @GET("search_info_content?")
    suspend fun getInfoPost(@Query("post_id")postId:String): Response<Any>

    @GET("/search_employ_content?")
    suspend fun getEmployPost(@Query("post_id")postId:String): Response<Any>

    @GET("/search_comment?")
    suspend fun getComment(@Query("post_id")postId: String): Response<Any>

    @GET("/write_comment")
    suspend fun registerComment(
            @Query("post_id") postId: String,
            @Query("user_id") userId: String,
            @Query("comment_content") commentContent: String,
            @Query("comment_id") commentId: String
    ): Response<Any>

    @GET("/check_writerOrNot")
    suspend fun isPostWriter(
            @Query("post_id")postId: String,
            @Query("user_id")userId: String
    ): Response<Any>

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