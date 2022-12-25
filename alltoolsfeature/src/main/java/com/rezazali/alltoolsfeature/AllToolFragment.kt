package com.rezazali.alltoolsfeature


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rezazali.alltoolsfeature.databinding.FragmentAllToolBinding
import com.rezazali.bmi.BMIActivity
import com.rezazali.calculator.CalculatorActivity
import com.rezazali.flashlight.FlashLightActivity
import com.rezazali.mapbrt.MapBrt
import com.rezazali.metro.MetroActivity
import com.rezazali.notepad.activities.NoteActivity
import com.rezazali.pdfreader.PdfActivity
import com.rezazali.qiba.qiba.CompassActivity
import com.rezazali.qiba.qiba.QibaActivity
import com.rezazali.scanner.ScannerActivity
import com.rezazali.speedometer.SpeedoActivity
import com.rezazali.stopwatch.StopWatchActivity
import com.rezazali.unitconverter.MainUnit
import com.rezazali.unitconverter.UnitsActivity


class AllToolFragment : Fragment() {


    lateinit var binding: FragmentAllToolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAllToolBinding.inflate(layoutInflater)


        binding.buttonNotePad.setOnClickListener {
            val intent = Intent(activity, NoteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.buttonCalculator.setOnClickListener {
            val intent = Intent(activity, CalculatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonSpeed.setOnClickListener {
            val intent = Intent(activity, SpeedoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }




        binding.buttonStopWatch.setOnClickListener {
            val intent = Intent(activity, StopWatchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonScanner.setOnClickListener {
            val intent = Intent(activity, ScannerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }




        binding.buttonQibla.setOnClickListener {
            val intent = Intent(activity, CompassActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonFlashLight.setOnClickListener {
            val intent = Intent(activity, FlashLightActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonBmi.setOnClickListener {
            val intent = Intent(activity, BMIActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonMetroMap.setOnClickListener {
            val intent = Intent(activity, MetroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonMapBrt.setOnClickListener {
            val intent = Intent(activity, MapBrt::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.buttonPdfReader.setOnClickListener {
            val intent = Intent(activity, PdfActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.buttonUnitConvert.setOnClickListener {
            val intent = Intent(activity, MainUnit::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }






        return binding.root
    }



}