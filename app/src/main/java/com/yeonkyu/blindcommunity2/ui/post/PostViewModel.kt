package com.yeonkyu.blindcommunity2.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun refreshPost(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                postId.value?.let {
                    val response = when(type){
                        1 -> repository.getFreePost(it) as ArrayList<LinkedTreeMap<String, String>>
                        2 -> repository.getInfoPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        3 -> repository.getEmployPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        else -> null
                    }
                    //Log.e("BC_CHECK","post response : $response")
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
        }
    }

    fun refreshComment(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postId.value?.let {
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
        }
    }


}