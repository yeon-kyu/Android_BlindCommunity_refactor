package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.PostService
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo
import com.yeonkyu.blindcommunity2.data.entities.CommentResponse
import com.yeonkyu.blindcommunity2.data.entities.PostResponse

class PostRepository(private val postService: PostService) : BaseRepository() {

    suspend fun getFreePost(postId:String): PostResponse =
            apiRequest { postService.getFreePostContent(postId) }

    suspend fun getInfoPost(postId: String): PostResponse =
            apiRequest { postService.getInfoPostContent(postId) }

    suspend fun getEmployPost(postId: String): PostResponse =
            apiRequest { postService.getEmployPostContent(postId) }

    suspend fun getComment(postId: String): CommentResponse =
            apiRequest { postService.getComment(postId) }

    suspend fun registerComment(commentInfo: CommentInfo): PostResponse =
            apiRequest { postService.registerComment(commentInfo) }

    suspend fun isPostWriter(postId: String, userId: String): PostResponse =
            apiRequest { postService.isPostWriter(postId,userId) }

    suspend fun deletePost(postId:String, postType: Int): Any =
            apiRequest { postService.deletePost(postId, postType) }

    suspend fun deleteComment(commentInfo: CommentInfo): CommentResponse =
            apiRequest { postService.deleteComment(commentInfo) }
}