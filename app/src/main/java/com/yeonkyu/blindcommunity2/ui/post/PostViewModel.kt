package com.yeonkyu.blindcommunity2.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    var type: Int? = null//1: free, 2: info, 3: employ

    val postId : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _nickname : MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }
    val nickname : LiveData<String>
        get() = _nickname

    private val _title = MutableLiveData<String>().apply {
        postValue("")
    }
    val title : LiveData<String>
        get() = _title

    private val _content = MutableLiveData<String>().apply {
        postValue("")
    }
    val content : LiveData<String>
        get() = _content

    fun refreshPost(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("BC_CHECK","type : $type, postId : ${postId.value}")
            try{
                postId.value?.let {
                    val response = when(type){
                        1 -> repository.getFreePost(it) as ArrayList<LinkedTreeMap<String, String>>
                        2 -> repository.getInfoPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        3 -> repository.getEmployPost(it) as ArrayList<LinkedTreeMap<String, String>>
                        else -> null
                    }
                    Log.e("BC_CHECK","response : $response")
                    response?.let {
                        _nickname.postValue(response[0]["nickname"])
                        _title.postValue(response[0]["title"])
                        _content.postValue(response[0]["content"])
                    }
                }
            }
            catch (e:Exception){
                Log.e("BC_ERROR","error : $e")
            }

        }
    }


}