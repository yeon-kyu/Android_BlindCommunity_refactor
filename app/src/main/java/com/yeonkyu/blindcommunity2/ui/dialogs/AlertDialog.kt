package com.yeonkyu.blindcommunity2.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.yeonkyu.blindcommunity2.R

class AlertDialog(context: Context) {
    private val mContext = context

    fun callFunction(titleMsg: String, confirmMsg:String, cancelMsg:String){
        val dig = Dialog(mContext)
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dig.setContentView(R.layout.dialog_alert)

        dig.setCancelable(false)

        val titleTv = dig.findViewById<TextView>(R.id.dialog_alert_title)
        val cancelTv = dig.findViewById<TextView>(R.id.dialog_alert_cancel)
        val confirmTv = dig.findViewById<TextView>(R.id.dialog_alert_confirm)

        titleTv.text = titleMsg
        cancelTv.text = cancelMsg
        confirmTv.text = confirmMsg

        cancelTv.setOnClickListener {
            dig.dismiss()
        }
    }
}