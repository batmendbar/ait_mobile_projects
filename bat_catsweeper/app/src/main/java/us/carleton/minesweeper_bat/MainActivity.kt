package us.carleton.minesweeper_bat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.VoicemailContract
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import us.carleton.minesweeper_bat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TRY = false
    val FLAG = true

    private lateinit var binding: ActivityMainBinding
    private var gameMode: Boolean = TRY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.radioMode.check(binding.radioTry.id)
        binding.btnReset.setOnClickListener{
            binding.minesweeperField.resetGame()
            binding.radioMode.check(binding.radioTry.id)
            gameMode = TRY
            setStatusText(resources.getString(R.string.active_game_text))
        }
    }

    public fun setStatusText(message: String) {
        binding.txtStatus.text = message
        Snackbar.make(binding.root,
            "${binding.txtStatus.text.toString()}",
            Snackbar.LENGTH_LONG).show()
    }

    public fun getMode(): Boolean = gameMode

    fun onRadioButtonClicked(view: View) {
        if (binding.radioTry.id == binding.radioMode.checkedRadioButtonId) {
            gameMode = TRY
        } else {
            gameMode = FLAG
        }
    }
}