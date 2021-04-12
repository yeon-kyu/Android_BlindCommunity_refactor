package com.yeonkyu.blindcommunity2.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.ui.board.BoardActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        home_free_board.setOnClickListener {
            val intent = Intent(activity, BoardActivity::class.java)
            intent.putExtra("type", "free")
            startActivity(intent)
        }

        home_info_board.setOnClickListener {
            val intent = Intent(activity, BoardActivity::class.java)
            intent.putExtra("type", "info")
            startActivity(intent)
        }
        home_employee_board.setOnClickListener {
            val intent = Intent(activity, BoardActivity::class.java)
            intent.putExtra("type", "employee")
            startActivity(intent)
        }

    }

}