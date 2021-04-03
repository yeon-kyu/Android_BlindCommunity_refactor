package com.yeonkyu.blindcommunity2.ui

import androidx.lifecycle.ViewModel
import com.yeonkyu.blindcommunity2.ApplicationClass
import com.yeonkyu.blindcommunity2.data.repository.SplashRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val repository:SplashRepository) : ViewModel(){

    fun autoLogin(){
        CoroutineScope(Dispatchers.IO).launch {

            delay(1000)

            val id = ApplicationClass.prefs.getId()
            if(id==""){

            }



//            try {
//                val response = repository.
//            }
        }
    }
}