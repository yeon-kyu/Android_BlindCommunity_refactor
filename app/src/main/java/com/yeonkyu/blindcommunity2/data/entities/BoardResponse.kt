package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class BoardResponse(
        @SerializedName(value = "result")
        val result: ArrayList<BoardInfo>,
        @SerializedName(value = "isSuccess")
        val isSuccess: Boolean,
        @SerializedName(value = "code")
        val code: Int,
        @SerializedName(value = "message")
        val message: String
)
