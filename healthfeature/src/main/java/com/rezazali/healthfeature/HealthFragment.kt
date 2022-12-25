package com.rezazali.healthfeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rezazali.bmi.BMIActivity
import com.rezazali.healthfeature.databinding.FragmentHealthBinding


class HealthFragment : Fragment() {


    lateinit var binding: FragmentHealthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHealthBinding.inflate(layoutInflater)


















        binding.buttonBmi.setOnClickListener {
            val intent = Intent(activity, BMIActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        return binding.root
    }



}