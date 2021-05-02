package com.yeonkyu.blindcommunity2.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    var hasBeenInit = false
    var type: Int? = null//1: free, 2: info, 3: employ
    val postId : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _nickname = MutableLiveData<String>()
    val nickname : LiveData<String>
        get() = _nickname

    private val _title = MutableLiveData<String>()
    val title : LiveData<String>
        get() = _title

    private val _content = MutableLiveData<String>()
    val content : LiveData<String>
        get() = _content

    private val _commentList = ArrayList<CommentInfo>()
    val commentList : MutableLiveData<ArrayList<CommentInfo>> by lazy{
        MutableLiveData<ArrayList<CommentInfo>>()
    }

    val registerCommentEt = MutableLiveData<String>()

    val isLoading : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            postValue(false)
        }
    }

    fun refreshPost(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                isLoading.postValue(true)
                postId.value?.let {
                    val response = when(type){
                        1 -> repository.getFreePost(it) as ArrayList<LinkedTreeMap<String, String>>
                        2 -> repository.getInfoPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        3 -> repository.getEmployPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        else -> null
                    }
                    response?.let {
                        _nickname.postValue(response[0]["nickname"])
                        _title.postValue(response[0]["title"])
                        _content.postValue(response[0]["content"])
                    }
                }
            }
            catch (e:Exception){
                Log.e("BC_ERROR","getPost error : $e")
            }
            finally {
                isLoading.postValue(false)
            }
        }
    }

    fun refreshComment(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                postId.value?.let {
                    _commentList.clear()
                    val response = repository.getComment(it)
                    if(response is ArrayList<*>){
                        val comments = response as ArrayList<LinkedTreeMap<String,String>>
                        Log.e("BC_CHECK","comments size : ${comments.size}")
                        for(comment in comments){
                            val nickname = comment["nickname"]!!
                            val content = comment["comment_content"]!!
                            val userId = comment["user_id"]!!
                            val commentId = comment["comment_id"]!!
                            _commentList.add(CommentInfo(nickname,content,userId,commentId))
                        }
                        commentList.postValue(_commentList)
                    }
                    else{
                        Log.e("BC_CHECK","response is not array")
                    }
                }
            }catch (e: Exception){
                Log.e("BC_ERROR","getComment error : $e")
            }
            finally {
                isLoading.postValue(false)
            }
        }
    }

    fun registerComment(){
        if(registerCommentEt.value?.length == 0){
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                val userId =  ApplicationClass.prefs.getId()
                val comment = registerCommentEt.value!!
                val sdfNow = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA)
                val commentId = sdfNow.format(Date(System.currentTimeMillis())) + userId

                val response = repository.registerComment(postId.value!!, userId,comment,commentId)
                Log.e("BC_CHECK","response: $response")
                if(response is Double){
                    when(response.toInt()) {
                        1 -> {
                            registerCommentEt.postValue("")
                            refreshComment()
                        }
                        else -> {
                            Log.e("BC_FAIL","registerComment failed")
                        }
                    }
                }
                else{
                    Log.e("BC_FAIL","registerComment failed")
                }
            }catch (e: Exception){
                Log.e("BC_ERROR","registerComment error : $e")
            }
            finally {
                isLoading.postValue(false)
            }
        }
    }
}