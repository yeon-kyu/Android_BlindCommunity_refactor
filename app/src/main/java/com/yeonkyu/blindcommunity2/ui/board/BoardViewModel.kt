package com.yeonkyu.blindcommunity2.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yeonkyu.blindcommunity2.data.entities.BoardTypeState
import com.yeonkyu.blindcommunity2.data.repository.BoardPagingSource
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.utils.Event

class BoardViewModel(private val repository: BoardRepository) : ViewModel(){

    var hasBeenInit = false

    private var boardType = BoardTypeState.None

    private val _writePostEvent = MutableLiveData<Event<String>>()
    val writePostEvent : LiveData<Event<String>>
        get() = _writePostEvent

    val boardFlow = Pager(PagingConfig(pageSize = 20)) {
        BoardPagingSource(boardType, repository.boardService)
    }.flow.cachedIn(viewModelScope)

    fun setBoardType(type: BoardTypeState){
        boardType = type
    }

    fun getBoardType(): Int{
        return boardType.type
    }

    fun writePost(){
        when(boardType){
            BoardTypeState.Free -> _writePostEvent.postValue(Event("자유 게시판"))
            BoardTypeState.Info -> _writePostEvent.postValue(Event("정보 게시판"))
            BoardTypeState.Employ -> _writePostEvent.postValue(Event("취업 게시판"))
        }
    }


}