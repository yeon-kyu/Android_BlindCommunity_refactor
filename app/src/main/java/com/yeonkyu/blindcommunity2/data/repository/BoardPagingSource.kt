package com.yeonkyu.blindcommunity2.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeonkyu.blindcommunity2.data.api.BoardService
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.entities.BoardTypeState

class BoardPagingSource(private val boardType: BoardTypeState, private val boardService: BoardService) : PagingSource<Int, BoardInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoardInfo> {
        return try{
            val nextPage = params.key ?: 0
            val response = when(boardType){
                BoardTypeState.Free -> boardService.getFreeBoard(nextPage).body()
                BoardTypeState.Info -> boardService.getInfoBoard(nextPage).body()
                BoardTypeState.Employ -> boardService.getEmployeeBoard(nextPage).body()
                else -> null
            }
            val data = response?.result as List<BoardInfo>

            Log.e("Board Paging", "size : ${data.size}, next Page : $nextPage")

            /**
             * 서버에서 한 페이지당20개씩이 아닌, LIMIT을 20으로 두고 OFFSET을 매개변수 nextPage로 받게 해두어서 nextPage + 20을 하게 했다.
             * android 공식 문서에는 nextPage는 0부터 시작하고, API 호출이 끝나면 +1을 한다.
             */
            LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = if(data.isEmpty()) null else nextPage + 20
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Board Paging Error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BoardInfo>): Int? { //refresh할 때 자동으로 호출된다.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}