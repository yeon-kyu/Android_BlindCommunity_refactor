package com.yeonkyu.blindcommunity2.ui.board

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val repository:BoardRepository) : ViewModel(){

    private val boardList = ArrayList<BoardInfo>()
    val liveBoardList: MutableLiveData<ArrayList<BoardInfo>> by lazy{
        MutableLiveData<ArrayList<BoardInfo>>()
    }
    private var count:Int = 0

    fun refresh(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                count = 0
                val response = repository.getFreeBoard(count)

                if(response is String){
                    Log.e("CHECK_TAG","response is string")
                }
                else{
                    Log.e("CHECK_TAG","response is Array")
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for(board in boardArray){
                        val nickname: String? = board["nickname"]
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        boardList.add(BoardInfo(postId,nickname,title))
                    }
                    liveBoardList.postValue(boardList)
                }
                Log.e("CHECK_TAG","$response")

            }catch (e:Exception){
                Log.e("ERROR_TAG","getFreeBoard api error $e")

            }
        }
    }

    fun loadNextBoards(){

    }
}