package com.navin.appbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.navin.appbox.adapter.BottomNavigationAdapter
import com.navin.appbox.databinding.FragmentHomeBinding
import com.rezazali.bazzarfeature.BazzarFragment
import com.rezazali.clubfeature.ClubFragment
import com.rezazali.gamefeature.GameFragment
import com.rezazali.toolfeature.ToolFragment


class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tool_menu -> {
                    binding.pager.currentItem = 0
                    binding.bottomNavigation.menu.findItem(R.id.tool_menu).isChecked = true
                }
                R.id.fun_menu -> {
                    binding.pager.currentItem = 1
                    binding.bottomNavigation.menu.findItem(R.id.fun_menu).isChecked = true
                }
                R.id.bazzar_menu -> {
                    binding.pager.currentItem = 2
                    binding.bottomNavigation.menu.findItem(R.id.bazzar_menu).isChecked = true
                }
                R.id.clop_menu -> {
                    binding.pager.currentItem = 3
                    binding.bottomNavigation.menu.findItem(R.id.clop_menu).isChecked = true
                }
            }
            false
        }










        val adapter =
            activity?.let { BottomNavigationAdapter(it.supportFragmentManager, lifecycle) }
        binding.pager.adapter = adapter



        binding.pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.bottomNavigation.menu.findItem(R.id.tool_menu).isChecked = true
                    1 -> binding.bottomNavigation.menu.findItem(R.id.fun_menu).isChecked = true
                    2 -> binding.bottomNavigation.menu.findItem(R.id.bazzar_menu).isChecked = true
                    3 -> binding.bottomNavigation.menu.findItem(R.id.clop_menu).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.pager.isUserInputEnabled = false



        return binding.root
    }


}