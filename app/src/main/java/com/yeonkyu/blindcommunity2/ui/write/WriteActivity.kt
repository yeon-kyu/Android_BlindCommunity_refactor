package com.yeonkyu.blindcommunity2.ui.write

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yeonkyu.blindcommunity2.R

class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        val type = intent.getStringExtra("type")
        type?.let {
            Log.e("CHECK_TAG",it)
        }
    }
}