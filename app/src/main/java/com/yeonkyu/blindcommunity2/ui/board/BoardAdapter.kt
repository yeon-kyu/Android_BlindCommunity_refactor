package com.yeonkyu.blindcommunity2.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BoardAdapter(context: Context) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private val mContext = context
    private val mBoardList = ArrayList<BoardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_board_list, parent, false)
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

    fun addLast(board: BoardInfo){
        mBoardList.add(board)
    }


    inner class BoardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val titleText = itemView.findViewById<TextView>(R.id.board_list_title)
        private val timeText = itemView.findViewById<TextView>(R.id.board_list_time)
        private val nickname = itemView.findViewById<TextView>(R.id.board_list_nickname)

        fun onBind(board: BoardInfo){
            super.itemView
            titleText.text = board.title
            nickname.text = board.nickname


            val boardTime = board.postId?.split("_")!!

            val dateNow = SimpleDateFormat("yyyyMMdd",Locale.KOREA)
            val timeNow = SimpleDateFormat("HHmmss",Locale.KOREA)
            val date = dateNow.format(Date(System.currentTimeMillis()))
            val time = timeNow.format(Date(System.currentTimeMillis()))

            if(date!=boardTime[0]){
                val yearGap = date.substring(0,4).toInt() - boardTime[0].substring(0,4).toInt()
                if(yearGap==0){
                    val temp = boardTime[0].substring(4,6)+"/"+boardTime[0].substring(6,8)+" "+boardTime[1].substring(0,2)+":"+boardTime[1].substring(2,4)
                    timeText.text = temp
                }
                else{
                    val temp = boardTime[0].substring(2,4)+"/"+boardTime[0].substring(4,6)+"/"+boardTime[0].substring(6,8)+" "+boardTime[1].substring(0,2)+":"+boardTime[1].substring(2,4)
                    timeText.text = temp
                }
            }
            else{
                val hourGap = time.substring(0,2).toInt() - boardTime[1].substring(0,2).toInt()
                if(hourGap==0){
                    val minGap = time.substring(2,4).toInt() - boardTime[1].substring(2,4).toInt()
                    val temp = "${minGap}분 전"
                    timeText.text = temp
                }
                else{
                    val temp = "${hourGap}시간 전"
                    timeText.text = temp
                }
            }

        }
    }
}