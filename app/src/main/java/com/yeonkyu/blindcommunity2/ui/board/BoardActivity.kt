package com.yeonkyu.blindcommunity2.ui.board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivityBoardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardActivity : AppCompatActivity(){

    private lateinit var mBinding: ActivityBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
    }

    private fun setupView(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = boardViewModel

        when(intent.getStringExtra("type")){
            "free" -> mBinding.boardTv.text = "자유 게시판"
            "info" -> mBinding.boardTv.text = "정보 게시판"
            "employee" -> mBinding.boardTv.text = "취업 게시판"
        }
    }
}