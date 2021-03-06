package com.yeonkyu.blindcommunity2.ui.board

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardTypeState
import com.yeonkyu.blindcommunity2.databinding.ActivityBoardBinding
import com.yeonkyu.blindcommunity2.ui.post.PostActivity
import com.yeonkyu.blindcommunity2.ui.write.WriteActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BoardActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()
    private lateinit var boardAdapter: BoardPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()

        if(!boardViewModel.hasBeenInit){
            boardViewModel.hasBeenInit = true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.boardBlurView.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        binding.boardBlurView.visibility = View.VISIBLE
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        binding.lifecycleOwner = this
        binding.viewModel = boardViewModel

        val boardRecyclerView: RecyclerView = binding.boardRecyclerview
        val linearLayoutManager = LinearLayoutManager(this)
        boardRecyclerView.layoutManager = linearLayoutManager

        boardAdapter = BoardPagingAdapter { board ->
            moveToPostActivity(board.postId!!)
        }

        boardRecyclerView.adapter = boardAdapter

        when(intent.getStringExtra("type")){
            "free" -> {
                binding.boardTv.text = "?????? ?????????"
                boardViewModel.boardType = BoardTypeState.Free
            }
            "info" -> {
                binding.boardTv.text = "?????? ?????????"
                boardViewModel.boardType = BoardTypeState.Info
            }
            "employee" -> {
                binding.boardTv.text = "?????? ?????????"
                boardViewModel.boardType = BoardTypeState.Employ
            }
        }

        lifecycleScope.launch {
            boardViewModel.boardFlow.collectLatest { pagingData ->
                boardAdapter.submitData(pagingData)
            }
        }

        binding.boardSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.primary))
        //mBinding.boardSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.rgb(0,165,165))

        binding.boardSwipeRefreshLayout.setOnRefreshListener {
            boardAdapter.refresh()
            binding.boardSwipeRefreshLayout.isRefreshing = false
        }

        binding.boardRefreshBt.setOnClickListener {
            boardAdapter.refresh()
        }
    }

    private fun setupViewModel(){
        boardViewModel.writePostEvent.observe(binding.lifecycleOwner!!) { event ->
            event.getContentIfNotHandled()?.let {
                val intent = Intent(this, WriteActivity::class.java)
                intent.putExtra("type",it)
                startActivityForResult(intent,100)
            }
        }
    }

    private fun moveToPostActivity(postId: String){
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("postId", postId)
        intent.putExtra("postType", boardViewModel.boardType)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                boardAdapter.refresh()
            }
        }
    }
}