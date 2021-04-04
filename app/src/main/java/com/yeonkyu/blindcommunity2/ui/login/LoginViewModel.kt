package com.yeonkyu.blindcommunity2.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.listeners.SplashListener
import com.yeonkyu.blindcommunity2.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val repository:LoginRepository) : ViewModel(){

    private var splashListener: SplashListener? = null

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

    fun setSplashListener(listener:SplashListener){
        splashListener = listener
    }

    fun autoLogin(){
        CoroutineScope(Dispatchers.IO).launch {

            delay(2000)

            val id = ApplicationClass.prefs.getId()
            if(id==""){
                splashListener?.onAutoLoginFailed()
                Log.e("BC_CHECK","no saved id")
                return@launch
            }
            val pw = ApplicationClass.prefs.getPw(id)
            if(pw==""){
                splashListener?.onAutoLoginFailed()
                Log.e("BC_CHECK","no saved pw")
                return@launch
            }

            try {
                val response = repository.login(id,pw)
                loginSuccessFlag.postValue(true)
                Log.e("BC_CHECK","auto Login result : $response")

            }catch (e:Exception){
                Log.e("BC_ERROR","auto login error $e")
            }
        }
    }

    fun login(){

        Log.e("!!.","!!@")

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

        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.login(userId,userPw)
            Log.e("BC_CHECK","login result : $response")
            if(response=="1"){ //success

            }
        }
    }

}