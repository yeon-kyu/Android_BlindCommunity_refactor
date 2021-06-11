package com.yeonkyu.blindcommunity2.ui.post

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo
import com.yeonkyu.blindcommunity2.databinding.ActivityPostBinding
import com.yeonkyu.blindcommunity2.ui.BaseActivity
import com.yeonkyu.blindcommunity2.ui.post.dialog.ActionDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity: BaseActivity(), DialogListener {

    private lateinit var binding: ActivityPostBinding
    private val postViewModel: PostViewModel by viewModel()
    private lateinit var commentAdapter: CommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getStringExtra("postId")
        val postType = intent.getIntExtra("postType",0)
        Log.e("BC_CHECK","postID : $postId")
        postViewModel.postId.value = postId

        postViewModel.type = postType

        setupView()
        setupViewModel()
        setMenuClickListener()

        if(!postViewModel.hasBeenInit) {
            postViewModel.refreshPost()
            postViewModel.refreshComment()
            postViewModel.hasBeenInit = true
        }
    }

    private fun setupView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.lifecycleOwner = this
        binding.viewModel = postViewModel

        val commentRecyclerview : RecyclerView = binding.postRecyclerview
        val linearLayoutManager = LinearLayoutManager(this)
        commentRecyclerview.layoutManager = linearLayoutManager

        commentAdapter = CommentAdapter()
        commentRecyclerview.adapter = commentAdapter

        binding.postBackBt.setOnClickListener {
            finish()
        }

        binding.postMoreBt.setOnClickListener {
            showDeletePostDialog()
        }

    }

    private fun setupViewModel(){
        postViewModel.commentList.observe(binding.lifecycleOwner!!,{
            commentAdapter.submitList(it.toMutableList())
        })

        postViewModel.registerCommentEt.observe(binding.lifecycleOwner!!,{
            if(it.isEmpty()){
                binding.postRegisterCommentBt.setTextColor(ContextCompat.getColor(this,R.color.gray))
            }
            else{
                binding.postRegisterCommentBt.setTextColor(ContextCompat.getColor(this,R.color.primary))
            }
        })

        postViewModel.alertEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let {
                showDialog(it,"확인",null)
            }

        })

        postViewModel.finishActivityEvent.observe(binding.lifecycleOwner!!,{ event ->
            event.getContentIfNotHandled()?.let {
                if(it) {
                    finish()
                }
            }
        })
    }

    private fun showDeletePostDialog(){
        val deletePostDialog = ActionDialog(this)
        deletePostDialog.deletePost(this)
    }

    private fun showDeleteCommentDialog(commentInfo: CommentInfo){
        val deleteCommentDialog = ActionDialog(this)
        deleteCommentDialog.deleteComment(this,commentInfo)
    }

    private fun setMenuClickListener(){
        commentAdapter.setMenuClickListener(object : CommentAdapter.OnMenuClickListener {
            override fun onClick(commentInfo: CommentInfo) {
                showDeleteCommentDialog(commentInfo)
            }
        })
    }

    override fun getDeletePostFlag() {
        postViewModel.deletePost()
    }

    override fun getDeleteCommentFlag(commentInfo: CommentInfo) {
        postViewModel.deleteComment(commentInfo)
    }
}