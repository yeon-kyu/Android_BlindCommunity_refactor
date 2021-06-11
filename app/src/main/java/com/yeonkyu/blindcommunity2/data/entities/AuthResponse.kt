package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "result")
    val result: Any,
    @SerializedName(value = "isSuccess")
    val isSuccess: Boolean,
    @SerializedName(value = "code")
    val code: Int,
    @SerializedName(value = "message")
    val message: String
)