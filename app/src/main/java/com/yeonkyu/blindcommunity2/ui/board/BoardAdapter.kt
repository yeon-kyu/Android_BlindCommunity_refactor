package com.yeonkyu.blindcommunity2.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo

class BoardAdapter(context: Context) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private val mContext = context
    private val boardList = ArrayList<BoardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_board_list,parent,false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(boardList[position])
    }

    override fun getItemCount(): Int {
        return boardList.size
    }


    inner class BoardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun onBind(board: BoardInfo){
            
        }
    }
}