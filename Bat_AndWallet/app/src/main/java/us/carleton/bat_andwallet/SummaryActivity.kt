package us.carleton.bat_andwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import us.carleton.bat_andwallet.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {
    lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val totalIncome = getIntent().getIntExtra("TotalIncome", 0)
        val totalExpense = getIntent().getIntExtra("TotalExpense", 0)
        val totalBalance = totalIncome - totalExpense

        binding.tvTotalIncome.text = resources.getString(R.string.total_income, totalIncome)
        binding.tvTotalExpense.text = resources.getString(R.string.total_expense, totalExpense)
        binding.tvTotalBalance.text = resources.getString(R.string.total_balance, totalBalance)

        binding.btnOK.setOnClickListener {
            val intentResult = Intent()
            setResult(AppCompatActivity.RESULT_OK, intentResult)
            finish()
        }
    }
}