package com.yeonkyu.blindcommunity2.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.CommentInfo
import com.yeonkyu.blindcommunity2.databinding.ItemCommentListBinding

open class CommentAdapter: ListAdapter<CommentInfo,CommentAdapter.CommentListViewHolder>(
        CommentDiffCallback
) {
    private var menuClickListener: OnMenuClickListener? = null

    interface OnMenuClickListener {
        fun onClick(commentId: String)
    }

    fun setMenuClickListener(listener: OnMenuClickListener){
        menuClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val binding: ItemCommentListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_comment_list,parent,false)
        return CommentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CommentListViewHolder(private val binding: ItemCommentListBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(commentInfo: CommentInfo) {
            binding.commentListMoreBt.setOnClickListener {
                menuClickListener?.onClick(commentInfo.commentId)
            }
            binding.commentItem = commentInfo
            binding.executePendingBindings()
        }
    }

    object CommentDiffCallback : DiffUtil.ItemCallback<CommentInfo>(){
        override fun areItemsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
            return oldItem.commentId == newItem.commentId
        }
        override fun areContentsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
            return oldItem == newItem
        }
    }
}