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
import com.yeonkyu.blindcommunity2.data.room_persistence.Favorites
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostViewModel(private val repository: PostRepository, private val db: FavoritesDao) : ViewModel() {

    var hasBeenInit = false
    var type: Int? = null//1: free, 2: info, 3: employ
    val postId = MutableLiveData<String>()

    val isStared = MutableLiveData<Boolean>()

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

    private val _alertEvent = MutableLiveData<Event<String>>()
    val alertEvent : LiveData<Event<String>>
        get() = _alertEvent

    private val _finishActivityEvent = MutableLiveData<Event<Boolean>>()
    val finishActivityEvent: LiveData<Event<Boolean>>
        get() = _finishActivityEvent

    fun refreshAll(){
        refreshPost()
        refreshComment()
    }

    fun refreshPost(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                isLoading.postValue(true)
                postId.value?.let {
                    val response = when(type){
                        1 -> repository.getFreePost(it)// as ArrayList<LinkedTreeMap<String, String>>
                        2 -> repository.getInfoPost(it)// as ArrayList<LinkedTreeMap<String, String>>
                        3 -> repository.getEmployPost(it)// as ArrayList<LinkedTreeMap<String, String>>
                        else -> null
                    }
                    response?.let {
                        if(response.isSuccess){
                            val postResult = response.result
                            if(postResult!=null){
                                _nickname.postValue(postResult.nickname)
                                _title.postValue(postResult.title)
                                _content.postValue(postResult.content)
                            }
                            else{
                                _alertEvent.postValue(Event("게시물을 찾지 못했습니다."))
                            }
                        }
                    }
                }
            }
            catch (e:Exception){
                Log.e("BC_ERROR","getPost error : $e")
                _alertEvent.postValue(Event("통신에 문제가 발생하였습니다."))
            }
            finally {
                isLoading.postValue(false)
            }
        }

        refreshStar()
    }

    private fun refreshStar(){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val postList  = db.getAllPost()
                isStared.postValue(false)
                for(item in postList){
                    if(item.postId == postId.value){
                        isStared.postValue(true)
                        break
                    }
                }
            }catch (e: Exception){
                Log.e("BC_ERROR","refresh star error $e")
            }
        }
    }

    fun insertOrDeleteStar(){
        viewModelScope.launch(Dispatchers.Default) {
            val favorite = Favorites(postId.value!!,nickname.value!!,title.value!!,type!!.toString())
            if(isStared.value == true){
                db.deletePost(favorite)
                isStared.postValue(false)
                Log.e("BC_CHECK","게시물이 찜 리스트에서 삭제되었습니다.")
            }
            else{
                db.insertPost(favorite)
                isStared.postValue(true)
                Log.e("BC_CHECK","게시물이 찜 리스트에 추가되었습니다.")
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
                    if(response.isSuccess){
                        _commentList.addAll(response.result)
                        commentList.postValue(_commentList)
                    }
                    else{
                        Log.e("BC_CHECK","get comment failed ${response.message}")
                        _alertEvent.postValue(Event(response.message))
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

                val commentInfo = CommentInfo(
                        postId = postId.value!!,
                        userId = userId,
                        comment = comment,
                        commentId = commentId,
                        nickname = null
                )

                val response = repository.registerComment(commentInfo)
                Log.e("BC_CHECK","response: $response")

                if(response.isSuccess){
                    registerCommentEt.postValue("")
                    refreshComment()
                }
                else{
                    Log.e("BC_FAIL","registerComment failed ${response.message}")
                    _alertEvent.postValue(Event(response.message))
                }
            }catch (e: Exception){
                Log.e("BC_ERROR","registerComment error : $e")
                _alertEvent.postValue(Event("서버와의 통신에 실패하였습니다."))
            }
            finally {
                isLoading.postValue(false)
            }
        }
    }

    fun deletePost(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postId.value?.let {
                    val isWriterResponse = repository.isPostWriter(it,ApplicationClass.prefs.getId())
                    if(isWriterResponse.isSuccess){
                        val response = repository.deletePost(postId.value!!, type!!)
                        if(response is Double) {
                            when (response.toInt()) {
                                -1 -> _alertEvent.postValue(Event("게시물 삭제에 실패하였습니다"))
                                1 -> _finishActivityEvent.postValue(Event(true))
                            }
                        }
                    }
                    else{
                        Log.e("BC_FAIL","deletePost failed $isWriterResponse.message")
                        _alertEvent.postValue(Event(isWriterResponse.message))
                    }
                }
            }
            catch (e: Exception){
                _alertEvent.postValue(Event("게시물을 삭제하던 중 문제가 발생하였습니다"))
            }
        }
    }

    fun deleteComment(commentInfo: CommentInfo){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(ApplicationClass.prefs.getId() == commentInfo.userId){
                    val response = repository.deleteComment(commentInfo.commentId)
                    if(response is Double){
                        when(response.toInt()){
                            1 -> refreshComment()
                            -1 -> _alertEvent.postValue(Event("댓글 삭제에 실패하였습니다."))
                        }
                    }
                }
                else{
                    _alertEvent.postValue(Event("댓글 작성자가 댓글을 지울 수 있습니다."))
                }

            }
            catch (e: Exception){
                _alertEvent.postValue(Event("댓글을 삭제하던 중 문제가 발생하였습니다."))
            }
        }
    }

}