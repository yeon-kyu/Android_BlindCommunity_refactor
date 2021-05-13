package com.yeonkyu.blindcommunity2.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.blindcommunity2.data.repository.AuthRepository
import com.yeonkyu.blindcommunity2.utils.Event
import java.util.regex.Pattern

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _alertEvent = MutableLiveData<Event<String>>()
    val alertEvent : LiveData<Event<String>>
        get() = _alertEvent

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






    }


}