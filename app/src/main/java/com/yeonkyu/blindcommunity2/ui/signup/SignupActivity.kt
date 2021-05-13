package com.yeonkyu.blindcommunity2.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivitySignupBinding
import com.yeonkyu.blindcommunity2.ui.BaseActivity
import com.yeonkyu.blindcommunity2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity: BaseActivity() {

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
        signupViewModel.alertEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let{
                showDialog(it,"확인",null)
            }
        })

        signupViewModel.signupEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", signupViewModel.id.value)
                startActivity(intent)
                finish()
            }
        })
    }
}