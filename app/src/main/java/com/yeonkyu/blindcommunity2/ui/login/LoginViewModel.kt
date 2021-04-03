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

    fun setSplashListener(listener:SplashListener){
        splashListener = listener
    }

    fun autoLogin(){
        CoroutineScope(Dispatchers.IO).launch {

            delay(2000)

            val id = ApplicationClass.prefs.getId()
            if(id==""){
                splashListener?.onAutoLoginFailed()
                return@launch
            }
            val pw = ApplicationClass.prefs.getPw(id)
            if(pw==""){
                splashListener?.onAutoLoginFailed()
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

}