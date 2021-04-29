package com.yeonkyu.blindcommunity2.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivityLoginBinding
import com.yeonkyu.blindcommunity2.ui.MainActivity
import com.yeonkyu.blindcommunity2.ui.dialogs.GrayAlertDialog
import com.yeonkyu.blindcommunity2.ui.signup.SignupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel

        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel(){
        //loginViewModel.setLoginListener(this)

        loginViewModel.id.observe(this,{
            loginViewModel.alertMsg.postValue("")
        })
        loginViewModel.pw.observe(this,{
            loginViewModel.alertMsg.postValue("")
        })

        loginViewModel.popUpEvent.observe(this,{ it ->
            it.getContentIfNotHandled()?.let{
                val alertDialog = GrayAlertDialog(this@LoginActivity)
                alertDialog.callFunction(it,"확인",null)
            }
        })

        loginViewModel.loginSuccessEvent.observe(this,{ event ->
            event.getContentIfNotHandled()?.let {
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("id", it)
                startActivity(intent)
                finish()
            }
        })
    }
}