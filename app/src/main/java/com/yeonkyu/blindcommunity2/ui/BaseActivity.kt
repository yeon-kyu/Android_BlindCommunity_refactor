package com.yeonkyu.blindcommunity2.ui

import androidx.appcompat.app.AppCompatActivity
import com.yeonkyu.blindcommunity2.ui.dialogs.GrayAlertDialog

open class BaseActivity : AppCompatActivity(){

    fun showDialog(title: String,confirmMsg: String, cancelMsg:String?){
        val dig = GrayAlertDialog(this)
        dig.callFunction(title, confirmMsg, cancelMsg)

    }
}