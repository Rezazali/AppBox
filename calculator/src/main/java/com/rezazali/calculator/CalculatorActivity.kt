package com.rezazali.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.rezazali.calculator.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalculatorBinding
    val ADDITION = '+'
    val SUBTRACTION = '-'
    val MULTIPLICATION = '*'
    val DIVISION = '/'
    val EQU = '='
    val EXTRA = '@'
    val MODULUS = '%'
    var ACTION = 0.toChar()
    var val1 = Double.NaN
    var val2 = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button1.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "1")
        }

        binding.button2.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "2")
        }

        binding.button3.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "3")
        }

        binding.button4.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "4")
        }

        binding.button5.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "5")
        }

        binding.button6.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "6")
        }

        binding.button7.setOnClickListener{
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "7")
        }

        binding.button8.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "8")
        }

        binding.button9.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "9")
        }

        binding.button0.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + "0")
        }

        binding.buttonDot.setOnClickListener {
            exceedLength()
            binding.input.setText(binding.input.getText().toString() + ".")
        }

        binding.buttonPara1.setOnClickListener {
            if (binding.input.getText().length > 0) {
                ACTION = MODULUS
                operation()
                if (!ifReallyDecimal()) {
                    binding.output.setText("$val1%")
                } else {
                    binding.output.setText(val1.toInt().toString() + "%")
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonAdd.setOnClickListener {
            if (binding.input.getText().length > 0) {
                ACTION = ADDITION
                operation()
                if (!ifReallyDecimal()) {
                    binding.output.setText(val1.toString() + "+")
                } else {
                    binding.output.setText(val1.toInt().toString() + "+")
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonSub.setOnClickListener {
            if (binding.input.getText().length > 0) {
                ACTION = SUBTRACTION
                operation()
                if (binding.input.getText().length > 0) if (!ifReallyDecimal()) {
                    binding.output.setText(val1.toString() + "-")
                } else {
                    binding.output.setText(val1.toInt().toString() + "-")
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonMulti.setOnClickListener {
            if (binding.input.getText().length > 0) {
                ACTION = MULTIPLICATION
                operation()
                if (!ifReallyDecimal()) {
                    binding.output.setText(val1.toString() + "×")
                } else {
                    binding.output.setText(val1.toInt().toString() + "×")
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonDivide.setOnClickListener {
            if (binding.input.getText().length > 0) {
                ACTION = DIVISION
                operation()
                if (ifReallyDecimal()) {
                    binding.output.setText(val1.toInt().toString() + "/")
                } else {
                    binding.output.setText(val1.toString() + "/")
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonPara2.setOnClickListener {
            if (!binding.output.getText().toString().isEmpty() || !binding.input.getText().toString().isEmpty()) {
                val1 = binding.input.getText().toString().toDouble()
                ACTION = EXTRA
                binding.output.setText("-" + binding.input.getText().toString())
                binding.input.setText("")
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonEqual.setOnClickListener {
            if (binding.input.getText().length > 0) {
                operation()
                ACTION = EQU
                if (!ifReallyDecimal()) {
                    binding.output.setText(val1.toString())
                } else {
                    binding.output.setText(val1.toInt().toString())
                }
                binding.input.setText(null)
            } else {
                binding.output.setText("Error")
            }
        }

        binding.buttonClear.setOnClickListener {
            if (binding.input.getText().length > 0) {
                val name: CharSequence = binding.input.getText().toString()
                binding.input.setText(name.subSequence(0, name.length - 1))
            } else {
                val1 = Double.NaN
                val2 = Double.NaN
                binding.input.setText("")
                binding.output.setText("")
            }
        }

        // Empty text views on long click.

        // Empty text views on long click.
        binding.buttonClear.setOnLongClickListener {
            val1 = Double.NaN
            val2 = Double.NaN
            binding.input.setText("")
            binding.output.setText("")
            true
        }



    }

    private fun operation() {
        if (!java.lang.Double.isNaN(val1)) {
            if (binding.output.getText().toString().get(0) == '-') {
                val1 = -1 * val1
            }
            val2 = binding.input.getText().toString().toDouble()
            when (ACTION) {
                ADDITION -> val1 = val1 + val2
                SUBTRACTION -> val1 = val1 - val2
                MULTIPLICATION -> val1 = val1 * val2
                DIVISION -> val1 = val1 / val2
                EXTRA -> val1 = -1 * val1
                MODULUS -> val1 = val1 % val2
                EQU -> {}
            }
        } else {
            val1 = binding.input.getText().toString().toDouble()
        }
    }

    // Remove error message that is already written there.
    private fun ifErrorOnOutput() {
        if (binding.output.getText().toString() == "Error") {
            binding.output.setText("")
        }
    }

    // Whether value if a double or not
    private fun ifReallyDecimal(): Boolean {
        return val1 == val1.toInt().toDouble()
    }

    private fun noOperation() {
        var inputExpression: String = binding.output.getText().toString()
        if (!inputExpression.isEmpty() && inputExpression != "Error") {
            if (inputExpression.contains("-")) {
                inputExpression = inputExpression.replace("-", "")
                binding.output.setText("")
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("+")) {
                inputExpression = inputExpression.replace("+", "")
                binding.output.setText("")
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("/")) {
                inputExpression = inputExpression.replace("/", "")
                binding.output.setText("")
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("%")) {
                inputExpression = inputExpression.replace("%", "")
                binding.output.setText("")
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("×")) {
                inputExpression = inputExpression.replace("×", "")
                binding.output.setText("")
                val1 = inputExpression.toDouble()
            }
        }
    }

    // Make text small if too many digits.
    private fun exceedLength() {
        if (binding.input.getText().toString().length > 10) {
            binding.input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }
    }


}