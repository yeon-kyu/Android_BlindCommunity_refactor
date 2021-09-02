package com.yeonkyu.blindcommunity2.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBoardListBinding

class BoardPagingAdapter(private val itemClick: (BoardInfo) -> Unit) : PagingDataAdapter<BoardInfo, BoardPagingViewHolder>(BoardDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardPagingViewHolder {
        val binding: ItemBoardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board_list, parent,false)
        return BoardPagingViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: BoardPagingViewHolder, position: Int) {
        holder.bind(getItem(position))
//        if(itemCount - position == 1){
//            endScrollListener?.onTouchEndScroll()
//        }
    }

    object BoardDiffCallback : DiffUtil.ItemCallback<BoardInfo>(){
        override fun areItemsTheSame(oldItem: BoardInfo, newItem: BoardInfo): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: BoardInfo, newItem: BoardInfo): Boolean {
            return oldItem == newItem
        }
    }
}