package com.yeonkyu.blindcommunity2.ui.board

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val repository:BoardRepository) : ViewModel(){

    private val boardList = ArrayList<BoardInfo>()
    val liveBoardList: MutableLiveData<ArrayList<BoardInfo>> by lazy{
        MutableLiveData<ArrayList<BoardInfo>>()
    }
    private var count:Int = 0
    private var boardType = 0 //1:자유게시판 2:정보게시판 3:취업게시판

    fun setBoardType(type:Int){
        boardType = type
    }

    fun refresh(){
        count = 0
        boardList.clear()
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
                val response = repository.getFreeBoard(count)

                if(response is ArrayList<*>) {
                    Log.e("CHECK_TAG", "response is Array")
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for (board in boardArray) {
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        boardList.add(BoardInfo(postId, nickname, title))
                    }
                    liveBoardList.postValue(boardList)
                    count += response.size
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
                    Log.e("CHECK_TAG","response is Array")
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for(board in boardArray){
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        boardList.add(BoardInfo(postId,nickname,title))
                    }
                    liveBoardList.postValue(boardList)
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
                    Log.e("CHECK_TAG","response is Array")
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for(board in boardArray){
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        boardList.add(BoardInfo(postId,nickname,title))
                    }
                    liveBoardList.postValue(boardList)
                    count += response.size
                }
            }catch (e:Exception){
                Log.e("ERROR_TAG","getEmployeeBoard api error $e")

            }
        }
    }
}