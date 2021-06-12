package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.PostService
import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse

class PostRepository(private val postService: PostService) : BaseRepository() {

    suspend fun getFreePost(postId:String): PostResponse =
            apiRequest { postService.getFreePostContent(postId) }

    suspend fun getInfoPost(postId: String): PostResponse =
            apiRequest { postService.getInfoPostContent(postId) }

    suspend fun getEmployPost(postId: String): PostResponse =
            apiRequest { postService.getEmployPostContent(postId) }

    suspend fun getComment(postId: String): Any =
            apiRequest { postService.getComment(postId) }

    suspend fun registerComment(postId:String, userId:String, commentContent:String, commentId:String): Any =
            apiRequest { postService.registerComment(postId,userId,commentContent,commentId) }

    suspend fun isPostWriter(postId: String, userId: String): Any =
            apiRequest { postService.isPostWriter(postId,userId) }

    suspend fun deletePost(postId:String, postType: Int): Any =
            apiRequest { postService.deletePost(postId, postType) }

    suspend fun deleteComment(commentId: String): Any =
            apiRequest { postService.deleteComment(commentId) }
}