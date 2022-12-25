package com.rezazali.showcasefeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rezazali.showcasefeature.databinding.FragmentShowcaseBinding
import com.rezazali.sudoku.MainMenuActivity


class ShowcaseFragment : Fragment() {

    lateinit var binding: FragmentShowcaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowcaseBinding.inflate(layoutInflater)




        binding.cardSudoku.setOnClickListener {
            val intent = Intent(activity, MainMenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }












        return binding.root
    }

}