package com.rezazali.publicfeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rezazali.flashlight.FlashLightActivity
import com.rezazali.publicfeature.databinding.FragmentPublicBinding
import com.rezazali.qiba.qiba.QibaActivity
import com.rezazali.scanner.ScannerActivity


class PublicFragment : Fragment() {


    lateinit var binding: FragmentPublicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPublicBinding.inflate(layoutInflater)


        binding.buttonScanner.setOnClickListener {
            val intent = Intent(activity, ScannerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.buttonQibla.setOnClickListener {
            val intent =Intent(activity, QibaActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonFlash.setOnClickListener {
            val intent =Intent(activity, FlashLightActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        return binding.root
    }



}