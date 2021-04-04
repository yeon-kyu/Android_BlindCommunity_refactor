package com.yeonkyu.blindcommunity2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yeonkyu.blindcommunity2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)

        NavigationUI.setupWithNavController(
                bottomNavigationView,
                findNavController(R.id.main_nav_host)
        )


    }
}