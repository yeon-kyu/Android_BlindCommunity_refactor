package com.yeonkyu.blindcommunity2.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.databinding.ItemBoardListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private val boardList = ArrayList<BoardInfo>()
    private var endScrollListener: EndScrollListener? = null

    interface EndScrollListener{
        fun onTouchEndScroll()
    }

    fun setEndScrollListener(listener: EndScrollListener){
        endScrollListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding:ItemBoardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_board_list,parent,false)
        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(boardList[position])
        if(boardList.size - position == 1){
            endScrollListener?.onTouchEndScroll()
        }
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    fun setBoardList(boardData: ArrayList<BoardInfo>){
        this.boardList.clear()
        this.boardList.addAll(boardData)
        notifyDataSetChanged()
    }

    fun clear(){
        boardList.clear()
        notifyDataSetChanged()
    }

    fun addLast(board: BoardInfo){
        boardList.add(board)
    }

    inner class BoardViewHolder(private val binding: ItemBoardListBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(board: BoardInfo){
            binding.boardItem = board
            binding.executePendingBindings()
        }
    }
}