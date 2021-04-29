package com.yeonkyu.blindcommunity2.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.FragmentAccountBinding
import com.yeonkyu.blindcommunity2.ui.board.BoardAdapter
import com.yeonkyu.blindcommunity2.ui.board.BoardListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val accountViewModel: AccountViewModel by viewModel()
    private lateinit var boardAdapter: BoardListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false)
        binding.lifecycleOwner = activity
        binding.viewModel = accountViewModel

        setupView()
        setupViewModel()
        accountViewModel.loadNextBoards()

        return binding.root
    }

    private fun setupView(){

    }

    private fun setupViewModel(){

    }
}