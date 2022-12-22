package us.carleton.bat_andwallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import us.carleton.bat_andwallet.databinding.ActivityMainBinding
import us.carleton.bat_andwallet.databinding.FinancialActivityBinding

class MainActivity : AppCompatActivity() {
    var totalIncome = 0
    var totalExpense = 0
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener{
            addActivity()
        }

        binding.btnSummary.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("TotalIncome", totalIncome)
            intent.putExtra("TotalExpense", totalExpense)
            startActivity(
                intent
            )
            //finish()
        }
    }

    private fun addActivity() {
        if(binding.etActivityDescription.text.toString().trim().isNullOrBlank()) {
            Snackbar.make(binding.root,
                resources.getString(R.string.empty_description_error),
                Snackbar.LENGTH_LONG).show()
            return
        }
        if (binding.etActivityAmount.text.toString().trim().isNullOrBlank()) {
            Snackbar.make(binding.root,
                resources.getString(R.string.empty_amount_error),
                Snackbar.LENGTH_LONG).show()
            return
        }
        val activityBinding = FinancialActivityBinding.inflate(layoutInflater)
        activityBinding.tvActivityDescription.text = binding.etActivityDescription.text
        val amount = binding.etActivityAmount.text.toString().toInt()
        activityBinding.tvActivityAmount.text = binding.etActivityAmount.text
        if (binding.tbActivityType.isChecked) {
            activityBinding.ivActivityIcon.setImageResource(R.drawable.rich_cat)
            totalIncome += amount
        } else {
            activityBinding.ivActivityIcon.setImageResource(R.drawable.poor_cat)
            totalExpense += amount
        }

        binding.llActivities.addView(activityBinding.root)
        binding.llActivities.post {
            binding.svScroller.fullScroll(View.FOCUS_DOWN)
        }

        activityBinding.btnRemove.setOnClickListener {
            val deleteAmount = activityBinding.tvActivityAmount.text.toString().toInt()
            if (binding.tbActivityType.isChecked) {
                totalIncome -= deleteAmount
            } else {
                activityBinding.ivActivityIcon.setImageResource(R.drawable.poor_cat)
                totalExpense -= deleteAmount
            }
            binding.llActivities.removeView(activityBinding.root)
        }
    }
}