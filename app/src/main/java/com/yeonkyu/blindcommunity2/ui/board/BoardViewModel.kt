package com.yeonkyu.blindcommunity2.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.repository.BoardPagingSource
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val repository:BoardRepository) : ViewModel(){

    var hasBeenInit = false

    private var boardType = 0 //1:자유게시판 2:정보게시판 3:취업게시판

    private val _writePostEvent = MutableLiveData<Event<String>>()
    val writePostEvent : LiveData<Event<String>>
        get() = _writePostEvent

    val boardFlow = Pager(PagingConfig(pageSize = 20)) {
        BoardPagingSource(boardType, repository.boardService)
    }.flow.cachedIn(viewModelScope)

    fun setBoardType(type:Int){
        boardType = type
    }
    fun getBoardType(): Int{
        return boardType
    }

//TODO refresh는 어떻게할지 다시 고민
//    fun refresh(){
//        count = 0
//        _boardList.clear()
//        loadNextBoards()
//    }

    fun writePost(){
        when(boardType){
            1 -> _writePostEvent.postValue(Event("자유 게시판"))
            2 -> _writePostEvent.postValue(Event("정보 게시판"))
            3 -> _writePostEvent.postValue(Event("취업 게시판"))
        }
    }
}