package com.yeonkyu.blindcommunity2.ui.post.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo

class ActionDialog(context: Context) {

    private val dig = Dialog(context)

    fun deletePost(listener: DialogListener){
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dig.setContentView(R.layout.dialog_choose_action)
        dig.show()

        val deleteTv = dig.findViewById<TextView>(R.id.dialog_action_delete_tv)
        deleteTv.setOnClickListener {
            dig.dismiss()
            listener.getDeletePostFlag()
        }
    }

    fun deleteComment(listener: DialogListener, commentInfo: CommentInfo){
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dig.setContentView(R.layout.dialog_choose_action)
        dig.show()

        val deleteTv = dig.findViewById<TextView>(R.id.dialog_action_delete_tv)
        deleteTv.setOnClickListener {
            dig.dismiss()
            listener.getDeleteCommentFlag(commentInfo)
        }
    }
}