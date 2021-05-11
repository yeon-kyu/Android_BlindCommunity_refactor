package com.yeonkyu.blindcommunity2.ui.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBelongedBoardListBinding
import com.yeonkyu.blindcommunity2.ui.account.BelongedBoardAdapter.*

class BelongedBoardAdapter: androidx.recyclerview.widget.ListAdapter<BoardInfo, BelongedBoardViewHolder>(
        BoardDiffCallback
) {

    private var onItemClickListener: OnItemClickListener? = null
    private var endScrollListener: EndScrollListener? = null

    interface OnItemClickListener{
        fun onItemClick(board: BoardInfo)
    }
    interface EndScrollListener{
        fun onTouchEndScroll()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelongedBoardViewHolder {
        val binding:ItemBelongedBoardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_belonged_board_list,parent,false)
        return BelongedBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BelongedBoardViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (itemCount - position == 1) {
            endScrollListener?.onTouchEndScroll()
        }
    }

    inner class BelongedBoardViewHolder(private val binding: ItemBelongedBoardListBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(boardInfo: BoardInfo) {
            binding.belongedBoardItem = boardInfo
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(boardInfo)
            }
            binding.executePendingBindings()
        }
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