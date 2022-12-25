package com.rezazali.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rezazali.bmi.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding :ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.skipResultBTN.setOnClickListener {
            startActivity( Intent(this@ResultActivity,  BMIActivity::class.java))
        }
        val bmi = intent.getDoubleExtra("bmi", -1.0)
        if (bmi == -1.0) {
            binding.containerL.visibility= View.GONE
        } else {
            binding.bmiValueTV.text = bmi.toString()
            if (bmi < 18.5) {
                binding.containerL.setBackgroundResource(R.color.colorYellow)
                binding.bmiFlagImgView.setImageResource(R.drawable.exclamation)
                binding.bmiLabelTV.text="You have an UnderWeight!"
                binding.commentTV.text="Here are some advices To help you increase your weight"
                binding.advice1IMG.setImageResource(R.drawable.nowater)
                binding.advice1TV.text="Don't drink water before meals"
                binding.advice2IMG.setImageResource(R.drawable.bigmeal)
                binding.advice2TV.text="Use bigger plates"
                binding.advice3TV.text="Get quality sleep"

            } else {
                if (bmi > 25) {
                    binding.containerL.setBackgroundResource(R.color.colorRed)
                    binding.bmiFlagImgView.setImageResource(R.drawable.warning)
                    binding.bmiLabelTV.text="You have an OverWeight!"
                    binding.commentTV.text="Here are some advices To help you decrease your weight"
                    binding.advice1IMG.setImageResource(R.drawable.water)
                    binding.advice1TV.text="Drink water a half hour before meals"
                    binding.advice2IMG.setImageResource(R.drawable.twoeggs)
                    binding.advice2TV.text="Eeat only two meals per day and make sure that they contains a high protein"
                    binding.advice3IMG.setImageResource(R.drawable.nosugar)
                    binding.advice3TV.text="Drink coffee or tea and Avoid sugary food"
                }
            }
        }
    }
}