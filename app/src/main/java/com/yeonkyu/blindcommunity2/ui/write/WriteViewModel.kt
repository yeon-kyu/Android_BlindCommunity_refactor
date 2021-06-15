package com.yeonkyu.blindcommunity2.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.entities.PostInfo
import com.yeonkyu.blindcommunity2.data.entities.PostResponse
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WriteViewModel(private val repository: BoardRepository) : ViewModel() {

    var postType:String? = null

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
            try {
                val postInfo = PostInfo(
                        postId = postId,
                        title = title,
                        content = content,
                        userId = ApplicationClass.prefs.getId(),
                        nickname = null
                )
                val response: PostResponse? = when (postType) {
                    "자유 게시판" -> repository.writeFreePost(postInfo)
                    "정보 게시판" -> repository.writeInfoPost(postInfo)
                    "취업 게시판" -> repository.writeEmployPost(postInfo)
                    else -> {
                        Log.e("BC_CHECK", "writePost fail")
                        _writeSuccessEvent.postValue(Event(false))
                        null
                    }
                }
                response?.let {
                    if (it.isSuccess) {
                        Log.e("BC_CHECK", "writePost success")
                        _writeSuccessEvent.postValue(Event(true))
                    } else {
                        Log.e("BC_CHECK", "writePost fail")
                        _writeSuccessEvent.postValue(Event(false))
                    }
                }
            }catch (e: Exception){
                Log.e("BC_ERROR","write post error $e")
            }
        }
    }
}