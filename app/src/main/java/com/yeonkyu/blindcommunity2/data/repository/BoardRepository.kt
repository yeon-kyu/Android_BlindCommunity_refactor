package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.BoardService

class BoardRepository(private val boardService: BoardService) : BaseRepository(){

    suspend fun getFreeBoard(cnt:Int): Any =
        apiRequest { boardService.getFreeBoard(cnt) }

    suspend fun getInfoBoard(cnt: Int): Any =
        apiRequest { boardService.getInfoBoard(cnt) }

    suspend fun getEmployeeBoard(cnt:Int): Any =
        apiRequest { boardService.getEmployeeBoard(cnt) }

    suspend fun getMyBoard(id: String): Any =
        apiRequest { boardService.getMyBoard(id) }

    suspend fun writeFreePost(
        postId: String,
        title: String,
        content: String,
        userId: String
    ): Any = apiRequest { boardService.writeFreePost(postId, title, content, userId) }

    suspend fun writeInfoPost(
        postId: String,
        title: String,
        content: String,
        userId: String
    ): Any = apiRequest { boardService.writeInfoPost(postId, title, content, userId) }

    suspend fun writeEmployPost(
        postId: String,
        title: String,
        content: String,
        userId: String
    ): Any = apiRequest { boardService.writeEmployPost(postId, title, content, userId) }

}