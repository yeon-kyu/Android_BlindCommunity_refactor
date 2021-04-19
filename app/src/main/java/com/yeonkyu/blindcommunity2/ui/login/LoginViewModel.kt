package com.yeonkyu.blindcommunity2.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.listeners.LoginListener
import com.yeonkyu.blindcommunity2.data.listeners.SplashListener
import com.yeonkyu.blindcommunity2.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val repository:LoginRepository) : ViewModel(){

    private var splashListener: SplashListener? = null
    private var loginListener: LoginListener? = null

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
    fun setLoginListener(listener:LoginListener){
        loginListener = listener
    }

    fun autoLogin(){

        viewModelScope.launch {
            delay(2000)

            val id = ApplicationClass.prefs.getId()
            if(id==""){
                splashListener?.onAutoLoginFailed()
                Log.e("BC_CHECK","no saved id")
                return@launch
            }
            val pw = ApplicationClass.prefs.getPw()
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
            try {
                val response = repository.login(userId,userPw)
                Log.e("BC_CHECK","login result : $response")

                when(response){
                    "1"-> {
                        loginListener?.onLoginSuccess(userId)//로그인 성공
                        ApplicationClass.prefs.setId(userId)
                        ApplicationClass.prefs.setPw(userPw)

                    }
                    "0"-> loginListener?.onLoginFail("아이디를 확인해주세요")//아이디 없음
                    "-1"->loginListener?.onLoginFail("비밀번호를 확인해주세요")//비밀번호 틀림
                }

            }catch (e:Exception){
                Log.e("BC_ERROR","api Fail $e")
            }
        }
    }

}