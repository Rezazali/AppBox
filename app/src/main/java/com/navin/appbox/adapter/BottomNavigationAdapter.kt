package com.navin.appbox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rezazali.bazzarfeature.BazzarFragment
import com.rezazali.clubfeature.ClubFragment
import com.rezazali.gamefeature.GameFragment
import com.rezazali.toolfeature.ToolFragment

class BottomNavigationAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle){

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> return ToolFragment()
            1 -> return GameFragment()
            2 -> return BazzarFragment()
        }
        return ClubFragment()
    }
}