package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class BoardResult(
        @SerializedName(value = "nickname")
        val nickname:String,
        @SerializedName(value = "title")
        val title:String,
        @SerializedName(value = "post_id")
        val post_id:String
)
