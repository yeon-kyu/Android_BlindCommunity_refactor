package com.yeonkyu.blindcommunity2.ui.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: BoardRepository): ViewModel() {

    private val _boardList = ArrayList<BoardInfo>()
    val boardList: MutableLiveData<ArrayList<BoardInfo>> by lazy{
        MutableLiveData<ArrayList<BoardInfo>>()
    }

    var hasData:Boolean = true

    fun loadAllMyBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val nickname = ApplicationClass.prefs.getId()
                val response = repository.getMyBoard(nickname)
                Log.e("CHECK_TAG", "$response")
                if (response is ArrayList<*>) {
                    val boardArray = response as ArrayList<LinkedTreeMap<String, String>>
                    for (board in boardArray) {
                        val title: String? = board["title"]
                        val postId: String? = board["post_id"]
                        val type: String? = board["post_type"]
                        _boardList.add(BoardInfo(postId, nickname, title,type))
                    }
                    boardList.postValue(_boardList)
                    hasData = true
                }
                else if(response is Double){
                    if(response.toInt()==0){
                        hasData = false
                    }
                }
            }
            catch (e: Exception){
                Log.e("BC_ERROR","loadAllMyBoards api error $e")
            }
        }
    }
}