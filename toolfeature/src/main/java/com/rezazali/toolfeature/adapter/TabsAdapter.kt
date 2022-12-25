package com.rezazali.toolfeature.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rezazali.alltoolsfeature.AllToolFragment
import com.rezazali.counterfeature.CounterFragment
import com.rezazali.engineeringfeature.EngineeringFragment
import com.rezazali.healthfeature.HealthFragment
import com.rezazali.publicfeature.PublicFragment
import com.rezazali.showcasefeature.ShowcaseFragment

class TabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {


    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {


        when(position){
            0 -> return ShowcaseFragment()
            1 -> return AllToolFragment()
            2 -> return EngineeringFragment()
            3 -> return HealthFragment()
            4 -> return CounterFragment()

        }
      return PublicFragment()
    }
}