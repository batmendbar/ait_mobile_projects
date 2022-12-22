package us.carleton.bat_stopwatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import us.carleton.bat_stopwatch.databinding.ActivityMainBinding
import us.carleton.bat_stopwatch.databinding.LapBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var elapsedMillis = 0
    private var myTimer = Timer()
    private var lapCount = 0
    private val updatePeriod = 10

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                binding.tvTimeElapsed.text = formatClock()
            }
            elapsedMillis += updatePeriod
        }
    }

    private fun formatClock() : String {
        val formatMillis = ((elapsedMillis % 1000) / 10)
        val formatSecond = ((elapsedMillis / 1000) % 60)
        val formatMinute = ((elapsedMillis / 1000 / 60) % 60)
        return String.format(resources.getString(R.string.time_formatter), formatMinute, formatSecond, formatMillis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        binding.btnReset.setOnClickListener {
            changeMyTimer()
            elapsedMillis = 0
            lapCount = 0
            binding.tvTimeElapsed.text = resources.getString(R.string.zero_clock)
            removeLaps()
        }

        binding.btnRun.setOnClickListener {
            changeMyTimer()
            val myTask = MyTimerTask()
            myTimer.schedule(myTask, 0, updatePeriod.toLong())
        }

        binding.btnStop.setOnClickListener {
            changeMyTimer()
        }

        binding.btnLap.setOnClickListener {
            addLap()
        }
    }

    private fun addLap() {
        val lapBinding = LapBinding.inflate(layoutInflater)
        lapCount++
        lapBinding.tvLapNumber.text = resources.getString(R.string.lap_number, lapCount.toString())
        lapBinding.tvLapTime.text = binding.tvTimeElapsed.text
        binding.llLaps.addView(lapBinding.root)
        binding.llLaps.post {
            binding.svScroller.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun removeLaps() {
        binding.llLaps.removeAllViews()
    }

    private fun changeMyTimer() {
        myTimer.cancel()
        myTimer = Timer()
    }
}