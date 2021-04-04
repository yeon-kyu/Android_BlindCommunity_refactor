package com.yeonkyu.blindcommunity2.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import com.yeonkyu.blindcommunity2.R

class GrayAlertDialog(context: Context) {
    private val mContext = context

    fun callFunction(titleMsg: String, confirmMsg:String, cancelMsg:String?){
        val dig = Dialog(mContext)
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dig.setContentView(R.layout.dialog_alert)

        dig.setCancelable(false)

        val titleTv = dig.findViewById<TextView>(R.id.dialog_alert_title)
        val cancelTv = dig.findViewById<TextView>(R.id.dialog_alert_cancel)
        val confirmTv = dig.findViewById<TextView>(R.id.dialog_alert_confirm)

        titleTv.text = titleMsg
        confirmTv.text = confirmMsg

        if(cancelMsg==null){ //확인버튼 1개

            cancelTv.visibility = View.GONE
            confirmTv.setOnClickListener {
                dig.dismiss()
            }
        }
        else{ //확인, 취소 버튼 각각 2개
            cancelTv.text = cancelMsg
            cancelTv.setOnClickListener {
                dig.dismiss()
            }
        }

        dig.show()
    }
}