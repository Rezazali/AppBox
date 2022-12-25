package com.rezazali.engineeringfeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rezazali.calculator.CalculatorActivity
import com.rezazali.engineeringfeature.databinding.FragmentEngineeringBinding
import com.rezazali.notepad.activities.NoteActivity
import com.rezazali.pdfreader.PdfActivity
import com.rezazali.speedometer.SpeedoActivity
import com.rezazali.stopwatch.StopWatchActivity


class EngineeringFragment : Fragment() {

    lateinit var binding: FragmentEngineeringBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEngineeringBinding.inflate(layoutInflater)



        binding.buttonCalculator.setOnClickListener {
            val intent = Intent(activity, CalculatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        binding.buttonStopwatch.setOnClickListener {
            val intent = Intent(activity, StopWatchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        binding.buttonNotepad.setOnClickListener {
            val intent = Intent(activity, NoteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnSpeed.setOnClickListener {
            val intent = Intent(activity, SpeedoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.buttonPdfReader.setOnClickListener {
            val intent = Intent(activity, PdfActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        return binding.root
    }

}