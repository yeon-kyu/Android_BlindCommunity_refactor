package com.yeonkyu.blindcommunity2.ui.board

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBoardListBinding

class BoardListAdapter: ListAdapter<BoardInfo, BoardListAdapter.BoardListViewHolder>(
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

    fun setItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }
    fun setEndScrollListener(listener: EndScrollListener){
        endScrollListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardListViewHolder {
        val binding:ItemBoardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board_list,parent,false)
        return BoardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardListViewHolder, position: Int) {
        holder.bind(getItem(position))
        if(itemCount - position == 1){
            endScrollListener?.onTouchEndScroll()
        }
    }

    inner class BoardListViewHolder(private val binding: ItemBoardListBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(boardInfo: BoardInfo) {
            binding.boardItem = boardInfo
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
