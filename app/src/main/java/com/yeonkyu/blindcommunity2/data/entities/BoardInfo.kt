package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class BoardInfo(
        @SerializedName(value = "post_id")
        val postId:String,
        @SerializedName(value = "nickname")
        val nickname:String,
        @SerializedName(value = "title")
        val title:String

)