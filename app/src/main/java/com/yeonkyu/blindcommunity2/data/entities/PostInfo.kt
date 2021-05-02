package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class PostInfo (
        @SerializedName(value = "nickname")
        val nickname: String,
        @SerializedName(value = "title")
        val title: String,
        @SerializedName(value = "content")
        val content: String
)