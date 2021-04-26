package com.yeonkyu.blindcommunity2.utils

class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set //allow external read but not write

    fun getContextIfNotHandled(): T? {
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}