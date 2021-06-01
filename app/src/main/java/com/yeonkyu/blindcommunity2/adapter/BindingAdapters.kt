package com.yeonkyu.blindcommunity2.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yeonkyu.blindcommunity2.R
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("timeMillis")
    fun TextView.getTime(postId: String?) {
        if(postId==null){
            text = ""
            return
        }

        val boardTime = postId.split("_")

        val dateNow = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val timeNow = SimpleDateFormat("HHmmss", Locale.KOREA)
        val date = dateNow.format(Date(System.currentTimeMillis()))
        val time = timeNow.format(Date(System.currentTimeMillis()))

        if (date != boardTime[0]) {
            val yearGap = date.substring(0, 4).toInt() - boardTime[0].substring(0, 4).toInt()
            text = if (yearGap == 0) {
                boardTime[0].substring(4, 6) + "/" + boardTime[0].substring(6, 8) + " " + boardTime[1].substring(0, 2) + ":" + boardTime[1].substring(2, 4)
            } else {
                boardTime[0].substring(2, 4) + "/" + boardTime[0].substring(4, 6) + "/" + boardTime[0].substring(6, 8) + " " + boardTime[1].substring(0, 2) + ":" + boardTime[1].substring(2, 4)
            }
        } else {
            val hourGap = time.substring(0, 2).toInt() - boardTime[1].substring(0, 2).toInt()
            text = if (hourGap == 0) {
                val minGap = time.substring(2, 4).toInt() - boardTime[1].substring(2, 4).toInt()
                "${minGap}분 전"
            } else {
                "${hourGap}시간 전"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun View.visibleIf(value:Boolean){
        visibility = when(value){
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("staredIf")
    fun ImageButton.staredIf(value:Boolean){
        if(value){
            setImageResource(R.drawable.icon_star_gold_32)
        }
        else{
            setImageResource(R.drawable.icon_star_black_24)
        }
    }
}
