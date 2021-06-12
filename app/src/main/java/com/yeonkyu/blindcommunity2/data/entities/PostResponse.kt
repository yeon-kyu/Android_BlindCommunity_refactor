package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName(value = "result")
    val result: PostInfo?,
    @SerializedName(value = "isSuccess")
    val isSuccess: Boolean,
    @SerializedName(value = "code")
    val code: Int,
    @SerializedName(value = "message")
    val message: String
)
