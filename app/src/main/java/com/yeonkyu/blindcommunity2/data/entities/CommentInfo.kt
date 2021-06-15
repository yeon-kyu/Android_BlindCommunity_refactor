package com.yeonkyu.blindcommunity2.data.entities

import com.google.gson.annotations.SerializedName

data class CommentInfo(
        @SerializedName(value = "nickname")
        val nickname: String?,
        @SerializedName(value = "comment_content")
        val comment: String,
        @SerializedName(value = "user_id")
        val userId: String,
        @SerializedName(value = "comment_id")
        val commentId: String,
        @SerializedName(value = "post_id")
        val postId: String?
)
