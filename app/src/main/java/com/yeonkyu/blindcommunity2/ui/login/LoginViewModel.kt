package com.yeonkyu.blindcommunity2.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.entities.LoginInfo
import com.yeonkyu.blindcommunity2.data.repository.AuthRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val repository:AuthRepository) : ViewModel(){

    private val _autoLoginEvent = MutableLiveData<Event<Boolean>>()
    val autoLoginEvent : LiveData<Event<Boolean>>
        get() = _autoLoginEvent

    private val _popUpEvent = MutableLiveData<Event<String>>()
    val popUpEvent: LiveData<Event<String>>
        get() = _popUpEvent

    private val _loginSuccessEvent = MutableLiveData<Event<String>>()
    val loginSuccessEvent: LiveData<Event<String>>
        get() = _loginSuccessEvent

    val loginSuccessFlag: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val id:MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }
    val pw:MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }
    val alertMsg:MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    fun autoLogin(){
        viewModelScope.launch {
            delay(1000)

            val id = ApplicationClass.prefs.getId()
            if(id.isEmpty()){
                _autoLoginEvent.postValue(Event(false))
                Log.e("BC_CHECK","no saved id")
                return@launch
            }
            val pw = ApplicationClass.prefs.getPw()
            if(pw.isEmpty()){
                _autoLoginEvent.postValue(Event(false))
                Log.e("BC_CHECK","no saved pw")
                return@launch
            }
            try {
                val response = repository.login(LoginInfo(id,pw))
                if(response.isSuccess){
                    loginSuccessFlag.postValue(true)
                }
                else{
                    _autoLoginEvent.postValue(Event(false))
                    Log.e("BC_FAIL","auto login failed ${response.message}")
                }
            }catch (e:Exception){
                Log.e("BC_ERROR","auto login error $e")
                _popUpEvent.postValue(Event("서버 통신에 실패하였습니다"))
            }
        }

    }

    fun login(){
        val userId = id.value.toString()
        val userPw = pw.value.toString()

        if(userId.isEmpty() || !Pattern.matches("^[a-zA-Z0-9]{5,15}\$", userId)){
            alertMsg.postValue("아이디는 알파벳과 숫자로 조합된\n 5~15글자이어야 합니다")
            return
        }
        if(userPw.isEmpty()|| !Pattern.matches("^[a-zA-Z0-9]{5,15}\$", userPw)){
            alertMsg.postValue("비밀번호는 알파벳과 숫자로 조합된\n 5~15글자이어야 합니다")
            return
        }

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = repository.login(LoginInfo(userId,userPw))
                if(response.isSuccess){
                    Log.e("BC_CHECK","login success")
                    _loginSuccessEvent.postValue(Event(userId))
                    ApplicationClass.prefs.setId(userId)
                    ApplicationClass.prefs.setPw(userPw)
                }
                else{
                    Log.e("BC_CHECK","login failed message : ${response.message}")
                    _popUpEvent.postValue(Event(response.message))
                }

            }catch (e:Exception){
                Log.e("BC_ERROR","api Fail $e")
            }
        }
    }

}