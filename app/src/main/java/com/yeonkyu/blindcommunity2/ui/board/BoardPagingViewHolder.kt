package com.yeonkyu.blindcommunity2.ui.board

import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBoardListBinding

class BoardPagingViewHolder(
        private val binding: ItemBoardListBinding,
        private val itemClick: (BoardInfo) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(boardInfo: BoardInfo?) {
        boardInfo?.let{
            binding.boardItem = boardInfo
            binding.root.setOnClickListener {
                itemClick(boardInfo)
            }
            binding.executePendingBindings()
        }
    }
}