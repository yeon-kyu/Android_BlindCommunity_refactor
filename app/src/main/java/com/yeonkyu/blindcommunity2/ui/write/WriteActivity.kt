package com.yeonkyu.blindcommunity2.ui.write

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivityWriteBinding
import com.yeonkyu.blindcommunity2.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteActivity : BaseActivity() {

    private lateinit var binding : ActivityWriteBinding
    private val writeViewModel: WriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_write)
        binding.lifecycleOwner = this
        binding.viewModel = writeViewModel

        val type = intent.getStringExtra("type")
        type?.let {
            binding.writePostTypeTv.text = type
            writeViewModel.postType = type
        }

        binding.writePostBackBt.setOnClickListener {
            finish()
        }
        binding.writePostCancelBt.setOnClickListener {
            finish()
        }

    }

    private fun setupViewModel(){
        writeViewModel.alertEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let {
                showDialog(it,"확인",null)
            }
        })

        writeViewModel.writeSuccessEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let{
                when(it){
                    true -> exit()
                    false -> showDialog("게시물 쓰기에 실패하였습니다","확인",null)
                }
            }
        })
    }

    private fun exit(){
        finish()
    }
}