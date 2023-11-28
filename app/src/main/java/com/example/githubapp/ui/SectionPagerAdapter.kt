package com.example.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity){

    private var fragment: Bundle

    init {
        fragment = data
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersListFragment()
            1 -> fragment = FollowingListFragment()
        }
        fragment?.arguments = this.fragment
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}