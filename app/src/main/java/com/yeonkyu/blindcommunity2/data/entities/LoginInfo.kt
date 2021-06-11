package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName(value="id")
    val id:String,
    @SerializedName(value="pw")
    val pw:String
)
