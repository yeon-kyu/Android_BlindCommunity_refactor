package com.yeonkyu.blindcommunity2.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.entities.BoardTypeState
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
    var boardType = BoardTypeState.None
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
                    val response = when(boardType){
                        BoardTypeState.Free -> repository.getFreePost(it)
                        BoardTypeState.Info -> repository.getInfoPost(it)
                        BoardTypeState.Employ -> repository.getEmployPost(it)
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
                                _alertEvent.postValue(Event("???????????? ?????? ???????????????."))
                            }
                        }
                    }
                }
            }
            catch (e:Exception){
                Log.e("BC_ERROR","getPost error : $e")
                _alertEvent.postValue(Event("????????? ????????? ?????????????????????."))
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
            val favorite = Favorites(postId.value!!,nickname.value!!,title.value!!,boardType.type.toString())
            if(isStared.value == true){
                db.deletePost(favorite)
                isStared.postValue(false)
                Log.e("BC_CHECK","???????????? ??? ??????????????? ?????????????????????.")
            }
            else{
                db.insertPost(favorite)
                isStared.postValue(true)
                Log.e("BC_CHECK","???????????? ??? ???????????? ?????????????????????.")
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
                _alertEvent.postValue(Event("???????????? ????????? ?????????????????????."))
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
                        val postInfo = BoardInfo(
                                postId = postId.value!!,
                                type = boardType.type!!.toString(),
                                nickname = null,
                                title = null
                        )

                        val deleteResponse = repository.deletePost(postInfo)
                        if(deleteResponse.isSuccess){
                            _finishActivityEvent.postValue(Event(true))
                        }
                        else{
                            _alertEvent.postValue(Event("????????? ????????? ?????????????????????"))
                            Log.e("BC_FAIL","delete post failed ${deleteResponse.message}")
                        }
                    }
                    else{
                        Log.e("BC_FAIL","deletePost failed $isWriterResponse.message")
                        _alertEvent.postValue(Event(isWriterResponse.message))
                    }
                }
            }
            catch (e: Exception){
                Log.e("BC_ERROR","delete post error $e")
                _alertEvent.postValue(Event("???????????? ???????????? ??? ????????? ?????????????????????"))
            }
        }
    }

    fun deleteComment(commentInfo: CommentInfo){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(ApplicationClass.prefs.getId() == commentInfo.userId){
                    val response = repository.deleteComment(commentInfo)
                    if(response.isSuccess){
                        refreshComment()
                    }
                    else{
                        Log.e("BC_FAIL","delete comment failed ${response.message}")
                        _alertEvent.postValue(Event("?????? ????????? ?????????????????????."))
                    }
                }
                else{
                    Log.e("BC_FAIL","deletePost failed : not writer of this comment")
                    _alertEvent.postValue(Event("?????? ???????????? ????????? ?????? ??? ????????????."))
                }
            }
            catch (e: Exception){
                _alertEvent.postValue(Event("????????? ???????????? ??? ????????? ?????????????????????."))
            }
        }
    }

}