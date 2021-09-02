package com.yeonkyu.blindcommunity2.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeonkyu.blindcommunity2.data.api.BoardService
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo

class BoardPagingSource(private val boardService: BoardService) : PagingSource<Int, BoardInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoardInfo> {
        return try{
            val nextPage = params.key ?: 0 //이건 무슨 용도인가
            val response = boardService.getFreeBoard(nextPage).body()
            val data = response?.result as List<BoardInfo>

            Log.e("Board Paging", "size : ${data.size}, next Page : $nextPage")

            LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = if(data.isEmpty()) null else nextPage + 20
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Board Paging Error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BoardInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}