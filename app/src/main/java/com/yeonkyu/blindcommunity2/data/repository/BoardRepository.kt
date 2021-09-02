package com.yeonkyu.blindcommunity2.data.repository

import com.yeonkyu.blindcommunity2.data.api.BoardService
import com.yeonkyu.blindcommunity2.data.entities.BoardResponse
import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse

class BoardRepository(val boardService: BoardService) : BaseRepository(){
    suspend fun getMyBoard(id: String): BoardResponse =
        apiRequest { boardService.getMyBoard2(id) }

    suspend fun writeFreePost(
        postInfo: PostInfo
    ): PostResponse = apiRequest { boardService.writeFreePost(postInfo) }

    suspend fun writeInfoPost(
        postInfo: PostInfo
    ): PostResponse = apiRequest { boardService.writeInfoPost(postInfo) }

    suspend fun writeEmployPost(
        postInfo: PostInfo
    ): PostResponse = apiRequest { boardService.writeEmployPost(postInfo) }
}