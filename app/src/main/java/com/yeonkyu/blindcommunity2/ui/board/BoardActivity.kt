package com.yeonkyu.blindcommunity2.ui.board

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivityBoardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardActivity : AppCompatActivity(){

    private lateinit var mBinding: ActivityBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()
    private lateinit var boardAdapter: BoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = boardViewModel

        val boardRecyclerView: RecyclerView = mBinding.boardRecyclerview
        val linearLayoutManager = LinearLayoutManager(this)
        boardRecyclerView.layoutManager = linearLayoutManager

        boardAdapter = BoardAdapter(this)
        boardRecyclerView.adapter = boardAdapter

        mBinding.boardRecyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(linearLayoutManager.findLastVisibleItemPosition()==boardAdapter.itemCount-1){
                    boardViewModel.loadNextBoards()
                }
            }
        })


        when(intent.getStringExtra("type")){
            "free" -> mBinding.boardTv.text = "자유 게시판"
            "info" -> mBinding.boardTv.text = "정보 게시판"
            "employee" -> mBinding.boardTv.text = "취업 게시판"
        }
    }

    private fun setupViewModel(){
        boardViewModel.refresh()

        boardViewModel.liveBoardList.observe(mBinding.lifecycleOwner!!,{
            boardAdapter.setBoardList(it)
        })
    }
}