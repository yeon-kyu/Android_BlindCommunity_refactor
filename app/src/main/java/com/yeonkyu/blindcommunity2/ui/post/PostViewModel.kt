package com.yeonkyu.blindcommunity2.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            Log.e("BC_CHECK","type : $type")
            try{
                postId.value?.let {
                    val response = when(type){
                        1 -> repository.getFreePost(it)
                        2 -> repository.getInfoPost(it)
                        3 -> repository.getEmployPost(it)
                        else -> null
                    }
                    Log.e("BC_CHECK","response : $response")
                }
            }
            catch (e:Exception){
                Log.e("BC_ERROR","error : $e")
            }

        }
    }


}