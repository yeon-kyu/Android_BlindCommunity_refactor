package com.yeonkyu.blindcommunity2.ui.post.dialog

import com.yeonkyu.blindcommunity2.data.entities.CommentInfo

interface DialogListener {
    fun getDeletePostFlag()
    fun getDeleteCommentFlag(commentInfo: CommentInfo)
}