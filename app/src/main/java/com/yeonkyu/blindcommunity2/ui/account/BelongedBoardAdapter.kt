package com.yeonkyu.blindcommunity2.ui.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBelongedBoardListBinding
import androidx.recyclerview.widget.ListAdapter

class BelongedBoardAdapter: ListAdapter<BoardInfo, BelongedBoardAdapter.BelongedBoardViewHolder>(
        BoardDiffCallback
) {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(board: BoardInfo)
    }

    fun setItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelongedBoardViewHolder {
        val binding:ItemBelongedBoardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_belonged_board_list, parent,false)
        return BelongedBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BelongedBoardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BelongedBoardViewHolder(private val binding: ItemBelongedBoardListBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(boardInfo: BoardInfo) {
            binding.belongedBoardItem = boardInfo
            binding.root.setOnClickListener {
                onItemClickListener!!.onItemClick(boardInfo)
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