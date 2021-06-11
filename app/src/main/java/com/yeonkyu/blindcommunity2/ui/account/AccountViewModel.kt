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

    val hasData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            postValue(true)
        }
    }

    fun loadAllMyBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _boardList.clear()
                val nickname = ApplicationClass.prefs.getId()
                val response = repository.getMyBoard(nickname)
                if(response.isSuccess){
                    _boardList.addAll(response.result)
                    boardList.postValue(_boardList)
                }
                else{
                    Log.e("BC_CHECK","getMyBoard api fail : ${response.message}")
                }
            }
            catch (e: Exception){
                Log.e("BC_ERROR","loadAllMyBoards api error $e")
            }
        }
    }
}