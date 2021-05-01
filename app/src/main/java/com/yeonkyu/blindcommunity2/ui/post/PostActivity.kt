package com.yeonkyu.blindcommunity2.ui.post

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.databinding.ActivityPostBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private val postViewModel: PostViewModel by viewModel()
    private lateinit var commentAdapter: CommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getStringExtra("postId")
        val postType = intent.getIntExtra("postType",0)
        Log.e("BC_CHECK","postID : $postId")
        postViewModel.postId.postValue(postId)

        postViewModel.type = postType

        setupView()
        setupViewModel()

        //postViewModel.refreshPost()
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.lifecycleOwner = this
        binding.viewModel = postViewModel

    }

    private fun setupViewModel(){

    }
}