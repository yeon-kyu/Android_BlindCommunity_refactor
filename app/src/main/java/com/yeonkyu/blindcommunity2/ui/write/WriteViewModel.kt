package com.yeonkyu.blindcommunity2.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WriteViewModel(private val repository: BoardRepository) : ViewModel() {

    private val _alertEvent = MutableLiveData<Event<String>>()
    val alertEvent : LiveData<Event<String>>
        get() = _alertEvent

    private val _writeSuccessEvent = MutableLiveData<Event<Boolean>>()
    val writeSuccessEvent : LiveData<Event<Boolean>>
        get() = _writeSuccessEvent

    val postTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    val postContent: MutableLiveData<String> by lazy{
        MutableLiveData<String>().apply{
            postValue("")
        }
    }

    fun writeConfirm(){
        val title = postTitle.value!!
        val content = postContent.value!!

        val sdfNow = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA)
        val postId = sdfNow.format(Date(System.currentTimeMillis())) + ApplicationClass.prefs.getId()

        if(title.isEmpty()){
            _alertEvent.postValue(Event("제목을 입력해 주세요"))
            return
        }
        if(content.isEmpty()){
            _alertEvent.postValue(Event("내용을 입력해 주세요"))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.writeFreePost(postId, title, content, ApplicationClass.prefs.getId())
            if(response is Double){
                when(response.toInt()){
                    1 -> {
                        Log.e("BC_CHECK","writePost success")
                        _writeSuccessEvent.postValue(Event(true))
                    }
                    0 -> {
                        Log.e("BC_CHECK","writePost fail")
                        _writeSuccessEvent.postValue(Event(false))
                    }
                    else -> {
                        Log.e("BC_CHECK","writePost error")
                        _writeSuccessEvent.postValue(Event(false))
                    }
                }
            }
            else{
                _writeSuccessEvent.postValue(Event(false))
            }
        }
    }

}