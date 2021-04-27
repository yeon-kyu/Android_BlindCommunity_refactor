package com.yeonkyu.blindcommunity2.utils

open class Event<T> {
    private var content: T? = null

    var hasBeenHandled = false
        private set //allow external read but not write

    fun set(_content: T){
        content = _content
    }

    fun getContextIfNotHandled(): T? {
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}