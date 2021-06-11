package com.yeonkyu.blindcommunity2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.FragmentFavoriteBinding
import com.yeonkyu.blindcommunity2.ui.account.BelongedBoardAdapter
import com.yeonkyu.blindcommunity2.ui.board.BoardListAdapter
import com.yeonkyu.blindcommunity2.ui.post.PostActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    lateinit var binding : FragmentFavoriteBinding
    private val favoriteViewModel : FavoriteViewModel by viewModel()
    private lateinit var boardAdapter: BelongedBoardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false)
        binding.lifecycleOwner = activity
        binding.viewModel = favoriteViewModel

        setupView()
        setupViewModel()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.loadFavoritePostList()
    }

    private fun setupView(){
        val favoriteRecyclerview = binding.favoriteRecyclerview
        favoriteRecyclerview.layoutManager = LinearLayoutManager(context)

        boardAdapter = BelongedBoardAdapter()
        favoriteRecyclerview.adapter = boardAdapter

//        binding.favoriteSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(),R.color.primary))
//        binding.favoriteSwipeRefreshLayout.setOnRefreshListener {
//            favoriteViewModel.loadFavoritePostList()
//            binding.favoriteSwipeRefreshLayout.isRefreshing = false
//        }

        setItemClickListener()
    }

    private fun setupViewModel(){
        favoriteViewModel.favoritePostList.observe(binding.lifecycleOwner!!,{
            Log.e("CHECK_TAG","favoriteList size : ${it.size}")
            boardAdapter.submitList(it)
        })
    }

    private fun setItemClickListener(){
        boardAdapter.setItemClickListener(object: BelongedBoardAdapter.OnItemClickListener{
            override fun onItemClick(board: BoardInfo) {
                Log.e("BC_CHECK","item clicked")
                moveToPostActivity(board)
            }
        })
    }
    private fun moveToPostActivity(board: BoardInfo){
        val intent = Intent(requireContext(), PostActivity::class.java)
        intent.putExtra("postId",board.postId)
        val type = when(board.type){
            "자유 게시판" -> 1
            "정보 게시판" -> 2
            "취업 게시판" -> 3
            else -> {
                Log.e("BC_ERROR","post_type error")
                0
            }
        }
        intent.putExtra("postType",type)
        startActivity(intent)
    }
}