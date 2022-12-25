package com.navin.appbox.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabLayoutAdapter(fragment: Fragment, private val listTab: List<Fragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return listTab.size
    }

    override fun createFragment(position: Int): Fragment {
        return listTab[position]
    }
}