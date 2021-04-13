package com.yeonkyu.blindcommunity2.ui.board

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo

class BoardAdapter(context: Context) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private val mContext = context
    private val mBoardList = ArrayList<BoardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_board_list,parent,false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(mBoardList[position])
    }

    override fun getItemCount(): Int {
        return mBoardList.size
    }

    fun setBoardList(boardList: ArrayList<BoardInfo>){
        mBoardList.clear()
        mBoardList.addAll(boardList)
        notifyDataSetChanged()
    }

    fun clear(){
        mBoardList.clear()
        notifyDataSetChanged()
    }

    fun addLast(board:BoardInfo){
        mBoardList.add(board)
    }


    inner class BoardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val titleText = itemView.findViewById<TextView>(R.id.board_list_title)
        private val timeText = itemView.findViewById<TextView>(R.id.board_list_time)
        private val nickname = itemView.findViewById<TextView>(R.id.board_list_nickname)

        fun onBind(board: BoardInfo){
            super.itemView
            titleText.text = board.title
            timeText.text = board.postId
            nickname.text = board.nickname
        }
    }
}