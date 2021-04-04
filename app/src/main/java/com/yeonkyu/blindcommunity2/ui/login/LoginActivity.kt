package com.yeonkyu.blindcommunity2.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.listeners.LoginListener
import com.yeonkyu.blindcommunity2.databinding.ActivityLoginBinding
import com.yeonkyu.blindcommunity2.ui.MainActivity
import com.yeonkyu.blindcommunity2.ui.dialogs.GrayAlertDialog
import com.yeonkyu.blindcommunity2.ui.signup.SignupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), LoginListener {

    private lateinit var mBinding:ActivityLoginBinding
    private val mViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        mBinding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel(){
        mViewModel.setLoginListener(this)

        mViewModel.id.observe(this,{
            mViewModel.alertMsg.postValue("")
        })
        mViewModel.pw.observe(this,{
            mViewModel.alertMsg.postValue("")
        })

    }

    override fun onLoginFail(msg: String) {
        runOnUiThread {
            val alertDialog = GrayAlertDialog(this@LoginActivity)
            alertDialog.callFunction(msg,"확인",null)
        }
    }

    override fun onLoginSuccess(id:String) {
        runOnUiThread {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
            finish()
        }
    }
}