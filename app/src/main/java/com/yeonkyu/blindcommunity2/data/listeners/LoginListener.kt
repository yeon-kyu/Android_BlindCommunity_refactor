package com.yeonkyu.blindcommunity2.data.listeners

interface LoginListener {
    fun onLoginFail(msg:String)
    fun onLoginSuccess(id:String)
}