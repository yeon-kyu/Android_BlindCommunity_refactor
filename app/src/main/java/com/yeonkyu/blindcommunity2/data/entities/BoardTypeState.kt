package com.yeonkyu.blindcommunity2.data.entities

enum class BoardTypeState(val type: Int) {
    Free(1),
    Info(2),
    Employ(3),
    None(0);

    fun getBoardType() = type
}