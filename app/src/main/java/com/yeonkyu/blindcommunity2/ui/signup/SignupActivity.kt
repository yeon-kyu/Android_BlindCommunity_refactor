package com.yeonkyu.blindcommunity2.ui.signup

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivitySignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity: AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.viewModel = signupViewModel

        binding.signupBackBt.setOnClickListener {
            finish()
        }
    }

    private fun setupViewModel(){

    }
}