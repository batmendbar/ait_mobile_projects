package us.carleton.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import us.carleton.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            val numA: Int = binding.numA.text.toString().toInt()
            val numB: Int = binding.numB.text.toString().toInt()
            val answer: Int = numA + numB
            binding.result.text = getString(R.string.result) + answer.toString()
        }

        binding.subtractBtn.setOnClickListener {
            val numA: Int = binding.numA.text.toString().toInt()
            val numB: Int = binding.numB.text.toString().toInt()
            val answer: Int = numA - numB
            binding.result.text = getString(R.string.result) + answer.toString()
        }
    }
}