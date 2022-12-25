package com.rezazali.toolfeature


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.rezazali.alltoolsfeature.AllToolFragment
import com.rezazali.counterfeature.CounterFragment
import com.rezazali.engineeringfeature.EngineeringFragment
import com.rezazali.healthfeature.HealthFragment
import com.rezazali.publicfeature.PublicFragment
import com.rezazali.showcasefeature.ShowcaseFragment
import com.rezazali.toolfeature.adapter.TabsAdapter

import com.rezazali.toolfeature.databinding.FragmentToolBinding


class ToolFragment : Fragment() {

    lateinit var binding: FragmentToolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentToolBinding.inflate(layoutInflater)






        binding.pager.adapter = activity?.let { TabsAdapter(it.supportFragmentManager,lifecycle) }

        binding.tabLayout.getTabAt(5)?.select()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    Log.d(TAG, "onTabSelected: ")
                    when(tab.position){

                        0-> {
                            binding.pager.currentItem = 5

                        }
                        1-> {
                            binding.pager.currentItem = 4

                        }
                        2-> {
                            binding.pager.currentItem = 3

                        }
                        3->{
                            binding.pager.currentItem = 2

                        }
                        4->{
                            binding.pager.currentItem = 1

                        }
                        5-> {

                            binding.pager.currentItem = 0
                        }

                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })









        /*binding.btnScanner.setOnClickListener {
            val intent =Intent(activity, ScannerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnCalculator.setOnClickListener {

            val intent =Intent(activity, CalculatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnQiba.setOnClickListener {
            val intent =Intent(activity, QibaActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.btnMetro.setOnClickListener {
            Log.d(TAG, "onCreateView: ")
            val intent =Intent(activity, MetroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.btnBmi.setOnClickListener {
            Log.d(TAG, "onCreateView: ")
            val intent =Intent(activity, BMIActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }*/




/*        binding.btnSudoku.setOnClickListener {
            Log.d(TAG, "onCreateView: ")
            val intent =Intent(activity, MainMenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }*/
        return binding.root
    }


}