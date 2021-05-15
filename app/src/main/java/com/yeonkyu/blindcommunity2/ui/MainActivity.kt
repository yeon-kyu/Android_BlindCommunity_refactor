package com.yeonkyu.blindcommunity2.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yeonkyu.blindcommunity2.R
import com.yeonkyu.blindcommunity2.ui.account.AccountFragment
import com.yeonkyu.blindcommunity2.ui.favorite.FavoriteFragment
import com.yeonkyu.blindcommunity2.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_view_pager.isUserInputEnabled = false
        main_view_pager.adapter = PagerAdapter(supportFragmentManager,lifecycle)
        main_view_pager.registerOnPageChangeCallback(PageChangeCallback())
        main_bottom_navigation.setOnNavigationItemSelectedListener { navigationSelected(it) }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.home_screen -> {
                main_view_pager.currentItem = 0
                return true
            }
            R.id.account_screen -> {
                main_view_pager.currentItem = 1
                return true
            }
            R.id.favorite_screen -> {
                main_view_pager.currentItem = 2
                return true
            }
        }
        return false
    }

    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 3
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> AccountFragment()
                2 -> FavoriteFragment()
                else -> error("no such position: $position")
            }
        }
    }

    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            main_bottom_navigation.selectedItemId = when (position) {
                0 -> R.id.home_screen
                1 -> R.id.account_screen
                2 -> R.id.favorite_screen
                else -> error("no such position: $position")
            }
        }
    }
}