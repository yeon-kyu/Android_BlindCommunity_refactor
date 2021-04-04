package com.yeonkyu.blindcommunity2.ui.signup

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yeonkyu.blindcommunity2.R

class SignupActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backBt = findViewById<Button>(R.id.signup_back_bt)
        backBt.setOnClickListener {
            finish()
        }
    }
}