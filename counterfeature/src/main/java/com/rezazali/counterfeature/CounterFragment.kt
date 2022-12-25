package com.rezazali.counterfeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.rezazali.counterfeature.databinding.FragmentCounterBinding


class CounterFragment : Fragment(), OnClickListener {



    lateinit var binding: FragmentCounterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCounterBinding.inflate(layoutInflater)




        binding.buttonRahgiri.setOnClickListener {
            val intent = Intent(activity, ShowWebActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           intent.putExtra("url","https://tracking.post.ir/")
            intent.putExtra("title","رهگیری مرسوله")
            startActivity(intent)
        }



        return binding.root
    }

    override fun onClick(v: View?) {

    }


}