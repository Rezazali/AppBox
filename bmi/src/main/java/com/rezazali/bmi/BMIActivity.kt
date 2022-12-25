package com.rezazali.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.rezazali.bmi.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    lateinit var binding: ActivityBmiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setIcon(R.drawable.logo)
        binding.calculateBtn.setOnClickListener {
            if (binding.heightEDTX.text.isNotEmpty() && binding.weightEDTX.text.isNotEmpty()) {
                val weight = binding.weightEDTX.text.toString().toDouble()
                val height = binding.heightEDTX.text.toString().toDouble()/100
                if (weight > 0 && weight < 600 && height >= 0.50 && height < 2.50) {
                    val intent = Intent(this@BMIActivity, ResultActivity::class.java)
                    intent.putExtra("bmi", calculateBMI(weight, height))
                    startActivity(intent)
                } else {
                    showErrorSnack("Incorrect Values")
                }
            } else {
                showErrorSnack("A filed is missing")
            }
        }
    }

    private fun showErrorSnack(errorMsg: String) {
        val snackbar = Snackbar.make(binding.container, "error : $errorMsg !", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundResource(R.color.colorRed)
        snackbar.show()
    }

    private fun calculateBMI(weight: Double, height: Double) = BigDecimal(weight / (height * height))
        .setScale(2, RoundingMode.HALF_EVEN).toDouble()

}
