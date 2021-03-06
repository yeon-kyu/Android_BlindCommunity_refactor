package com.yeonkyu.blindcommunity2.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivitySplashBinding
import com.yeonkyu.blindcommunity2.ui.BaseActivity
import com.yeonkyu.blindcommunity2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()

        loginViewModel.autoLogin()
    }

    private fun setupView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel
    }

    private fun setupViewModel(){
        loginViewModel.loginSuccessFlag.observe(this,{
            if(it==true){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        loginViewModel.autoLoginEvent.observe(binding.lifecycleOwner!!,{
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        loginViewModel.popUpEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let{
                showDialog(it,"확인",null)
            }
        })
    }
}