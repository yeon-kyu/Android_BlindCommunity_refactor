package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.BoardService
import com.yeonkyu.blindcommunity2.data.entities.BoardResponse

class BoardRepository(private val boardService: BoardService) : BaseRepository(){

    suspend fun getFreeBoard(cnt:Int): BoardResponse =
        apiRequest { boardService.getFreeBoard(cnt) }

    suspend fun getInfoBoard(cnt: Int): BoardResponse =
        apiRequest { boardService.getInfoBoard(cnt) }

    suspend fun getEmployeeBoard(cnt:Int): BoardResponse =
        apiRequest { boardService.getEmployeeBoard(cnt) }

    suspend fun getMyBoard(id: String): BoardResponse =
        apiRequest { boardService.getMyBoard2(id) }


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