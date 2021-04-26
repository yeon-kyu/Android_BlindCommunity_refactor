package com.yeonkyu.blindcommunity2.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ActivityBoardBinding
import com.yeonkyu.blindcommunity2.ui.login.LoginActivity
import com.yeonkyu.blindcommunity2.ui.post.PostActivity
import com.yeonkyu.blindcommunity2.ui.write.WriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class BoardActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()
    private lateinit var boardAdapter: BoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        binding.lifecycleOwner = this
        binding.viewModel = boardViewModel

        val boardRecyclerView: RecyclerView = binding.boardRecyclerview
        val linearLayoutManager = LinearLayoutManager(this)
        boardRecyclerView.layoutManager = linearLayoutManager

        boardAdapter = BoardAdapter()
        boardRecyclerView.adapter = boardAdapter

        setItemClickListener()
        setEndScrollListener()

        binding.boardSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.primary))
        //mBinding.boardSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.rgb(0,165,165))

        binding.boardSwipeRefreshLayout.setOnRefreshListener {
            boardViewModel.refresh()
            binding.boardSwipeRefreshLayout.isRefreshing = false
        }

        binding.boardRefreshBt.setOnClickListener {
            boardViewModel.refresh()
        }

        when(intent.getStringExtra("type")){
            "free" -> {
                binding.boardTv.text = "자유 게시판"
                boardViewModel.setBoardType(1)
            }
            "info" -> {
                binding.boardTv.text = "정보 게시판"
                boardViewModel.setBoardType(2)
            }
            "employee" -> {
                binding.boardTv.text = "취업 게시판"
                boardViewModel.setBoardType(3)
            }
        }
    }

    private fun setupViewModel(){
        boardViewModel.refresh()

        boardViewModel.liveBoardList.observe(binding.lifecycleOwner!!, {
            Log.e("CHECK_TAG","liveboardlist observed")
            boardAdapter.setBoardList(it)
        })

        boardViewModel.writePostEvent.observe(binding.lifecycleOwner!!,{
            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("type",it.getContextIfNotHandled())
            startActivity(intent)
        })
    }

    private fun setEndScrollListener(){
        boardAdapter.setEndScrollListener(object : BoardAdapter.EndScrollListener{
            override fun onTouchEndScroll() {
                boardViewModel.loadNextBoards()
            }
        })
    }

    private fun setItemClickListener(){
        boardAdapter.setItemClickListener(object: BoardAdapter.OnItemClickListener{
            override fun onItemClick(board: BoardInfo) {
                moveToPostActivity(board.postId!!)
            }
        })
    }

    fun moveToPostActivity(postId: String){
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }
}