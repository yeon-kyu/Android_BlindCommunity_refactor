package com.yeonkyu.blindcommunity2.ui.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: BoardRepository): ViewModel() {

    fun loadNextBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMyBoard(ApplicationClass.prefs.getId())
            Log.e("CHECK_TAG","$response")
        }
    }
}