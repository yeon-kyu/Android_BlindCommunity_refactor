package com.yeonkyu.blindcommunity2.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val accountViewModel: AccountViewModel by viewModel()
    private lateinit var boardAdapter: BelongedBoardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false)
        binding.lifecycleOwner = activity
        binding.viewModel = accountViewModel

        setupView()
        setupViewModel()
        accountViewModel.loadAllMyBoards()

        return binding.root
    }

    private fun setupView(){

        val accountRecyclerview = binding.accountRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        accountRecyclerview.layoutManager = linearLayoutManager

        boardAdapter = BelongedBoardAdapter()
        accountRecyclerview.adapter = boardAdapter

        binding.accountSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(),R.color.primary))
        binding.accountSwipeRefreshLayout.setOnRefreshListener {
            accountViewModel.loadAllMyBoards()
            binding.accountSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupViewModel(){
        accountViewModel.boardList.observe(binding.lifecycleOwner!!,{
            boardAdapter.submitList(it.toMutableList())
        })

    }
}