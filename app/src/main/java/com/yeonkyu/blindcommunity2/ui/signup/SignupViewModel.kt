package com.yeonkyu.blindcommunity2.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.repository.AuthRepository
import com.yeonkyu.blindcommunity2.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _alertEvent = MutableLiveData<Event<String>>()
    val alertEvent : LiveData<Event<String>>
        get() = _alertEvent

    private val _signupEvent = MutableLiveData<Event<Boolean>>()
    val signupEvent : LiveData<Event<Boolean>>
        get() = _signupEvent

    val id: MutableLiveData<String> by lazy{
        MutableLiveData<String>().apply{
            postValue("")
        }
    }

    val pw: MutableLiveData<String> by lazy{
        MutableLiveData<String>().apply{
            postValue("")
        }
    }

    val nickname: MutableLiveData<String> by lazy{
        MutableLiveData<String>().apply{
            postValue("")
        }
    }

    val age: MutableLiveData<String> by lazy{
        MutableLiveData<String>().apply{
            postValue("")
        }
    }

    fun confirm(){
        id.value?.let {
            if(it.isEmpty() || !Pattern.matches("^[a-zA-Z0-9]{2,29}\$", it)){
                _alertEvent.postValue(Event("아이디는 2글자~30이내의 알파벳,숫자만 가능합니다."))
                return@confirm
            }
        }
        pw.value?.let {
            if(it.isEmpty() || !Pattern.matches("^[a-zA-Z0-9]{2,49}\$", it)){
                _alertEvent.postValue(Event("비밀번호는 2글자~50이내의 알파벳,숫자만 가능합니다."))
                return@confirm
            }
        }
        nickname.value?.let {
            if(it.isEmpty() || !Pattern.matches("^[a-zA-Z0-9]{2,49}\$", it)){
                _alertEvent.postValue(Event("닉네임은 2글자~50이내의 알파벳,숫자만 가능합니다."))
                return@confirm
            }
        }
        age.value?.let {
            if(it.isEmpty() || !Pattern.matches("^[0-9]{1,3}\$", it)){
                _alertEvent.postValue(Event("나이는 숫자만 입력이 가능합니다."))
                return@confirm
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.signup(id.value!!,pw.value!!,nickname.value!!,age.value!!)
            Log.e("BC_CHECK","signup response : $response")
            when(response){
                "0" -> _alertEvent.postValue(Event("중복된 아이디가 있습니다"))
                "1" -> {
                    _signupEvent.postValue(Event(true))
                    ApplicationClass.prefs.setId(id.value!!)
                    ApplicationClass.prefs.setPw(pw.value!!)
                }
                "-1" -> _alertEvent.postValue(Event("서버에 문제가 발생했습니다."))
            }
        }
    }

}