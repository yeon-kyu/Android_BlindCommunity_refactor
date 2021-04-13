package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.BoardService

class BoardRepository(private val boardService: BoardService) : BaseRepository(){
    suspend fun getFreeBoard(cnt:Int): Any =
        apiRequest { boardService.getFreeBoard(cnt) }

}