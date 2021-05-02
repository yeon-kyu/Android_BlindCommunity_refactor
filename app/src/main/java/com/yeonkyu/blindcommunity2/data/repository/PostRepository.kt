package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.PostService
import com.yeonkyu.blindcommunity2.data.entities.PostInfo

class PostRepository(private val postService: PostService) : BaseRepository() {

    suspend fun getFreePost(postId:String): Any =
            apiRequest { postService.getFreePost(postId) }

    suspend fun getInfoPost(postId: String): Any =
            apiRequest { postService.getInfoPost(postId) }

    suspend fun getEmployPost(postId: String): Any =
            apiRequest { postService.getEmployPost(postId) }

    suspend fun getComment(postId: String): Any =
            apiRequest { postService.getComment(postId) }

    suspend fun registerComment(postId:String, userId:String, commentContent:String, commentId:String): Any =
            apiRequest { postService.registerComment(postId,userId,commentContent,commentId) }
}