package us.carleton.bat_weather_report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import us.carleton.bat_weather_report.adapter.CityAdapter
import us.carleton.bat_weather_report.data.City
import us.carleton.bat_weather_report.databinding.ActivityScrollingBinding
import us.carleton.bat_weather_report.dialog.CityDialog

class ScrollingActivity : AppCompatActivity(), CityDialog.CityDialogHandler{

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CityAdapter(this)
        binding.recyclerCityList.adapter = adapter

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            val cityDialog = CityDialog()
            cityDialog.show(supportFragmentManager, "CityDialog")
        }
    }

    override fun cityCreated(city: City) {
        adapter.addCity(city)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}