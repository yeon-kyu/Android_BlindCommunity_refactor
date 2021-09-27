package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.PostService
import com.yeonkyu.blindcommunity2.data.entities.*
import com.yeonkyu.blindcommunity2.data.room_persistence.Favorites
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao

class PostRepository(private val postService: PostService, private val favoritesDao: FavoritesDao) : BaseRepository() {

    suspend fun loadPost(postId: String, typeState: BoardTypeState): PostResponse?{
        return when(typeState){
            BoardTypeState.Free -> apiRequest { postService.getFreePostContent(postId) }
            BoardTypeState.Info -> apiRequest { postService.getInfoPostContent(postId) }
            BoardTypeState.Employ -> apiRequest { postService.getEmployPostContent(postId) }
            else -> null
        }
    }

    suspend fun getComment(postId: String): CommentResponse =
            apiRequest { postService.getComment(postId) }

    suspend fun registerComment(commentInfo: CommentInfo): PostResponse =
            apiRequest { postService.registerComment(commentInfo) }

    suspend fun isPostWriter(postId: String, userId: String): PostResponse =
            apiRequest { postService.isPostWriter(postId,userId) }

    suspend fun deletePost(boardInfo: BoardInfo): PostResponse =
            apiRequest { postService.deletePost(boardInfo) }

    suspend fun deleteComment(commentInfo: CommentInfo): CommentResponse =
            apiRequest { postService.deleteComment(commentInfo) }

    fun getAllPost() = favoritesDao.getAllPost()

    fun deletePost(favorite: Favorites) = favoritesDao.deletePost(favorite)

    fun insertPost(favorite: Favorites) = favoritesDao.insertPost(favorite)
}