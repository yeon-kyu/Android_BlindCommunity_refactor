package com.yeonkyu.blindcommunity2.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val repository:BoardRepository) : ViewModel(){

    private val _boardList = ArrayList<BoardInfo>()
    val boardList: MutableLiveData<ArrayList<BoardInfo>> by lazy{
        MutableLiveData<ArrayList<BoardInfo>>()
    }

    private var count:Int = 0
    private var boardType = 0 //1:자유게시판 2:정보게시판 3:취업게시판

    private val _writePostEvent = MutableLiveData<Event<String>>()
    val writePostEvent : LiveData<Event<String>>
        get() = _writePostEvent

    fun setBoardType(type:Int){
        boardType = type
    }

    fun refresh(){
        count = 0
        _boardList.clear()
        loadNextBoards()
    }

    fun loadNextBoards(){
        when(boardType){
            1 -> loadNextFreeBoards()
            2 -> loadNextInfoBoards()
            3 -> loadNextEmployeeBoards()
        }
    }

    private fun loadNextFreeBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("BC_CHECK","count : $count")
                val response = repository.getFreeBoard(count)

                if(response is ArrayList<*>) {
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for (board in boardArray) {
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        _boardList.add(BoardInfo(postId, nickname, title))
                    }
                    boardList.postValue(_boardList)
                    count += response.size
                    Log.e("BC_CHECK","lvieboardlist size : ${boardList.value?.size}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_TAG", "getFreeBoard api error $e")
            }
        }
    }

    private fun loadNextInfoBoards(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = repository.getInfoBoard(count)

                if(response is ArrayList<*>){
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for(board in boardArray){
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        _boardList.add(BoardInfo(postId,nickname,title))
                    }
                    boardList.postValue(_boardList)
                    count += response.size
                }
            }catch (e:Exception){
                Log.e("ERROR_TAG","getInfoBoard api error $e")
            }
        }
    }

    private fun loadNextEmployeeBoards(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = repository.getEmployeeBoard(count)

                if(response is ArrayList<*>){
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for(board in boardArray){
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        _boardList.add(BoardInfo(postId,nickname,title))
                    }
                    boardList.postValue(_boardList)
                    count += response.size
                }
            }catch (e:Exception){
                Log.e("ERROR_TAG","getEmployeeBoard api error $e")
            }
        }
    }

    fun writePost(){
        when(boardType){
            1 -> _writePostEvent.postValue(Event("자유 게시판"))
            2 -> _writePostEvent.postValue(Event("정보 게시판"))
            3 -> _writePostEvent.postValue(Event("취업 게시판"))
        }
    }
}