package us.carleton.bat_weather_report.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import us.carleton.bat_weather_report.WeatherActivity
import us.carleton.bat_weather_report.ScrollingActivity
import us.carleton.bat_weather_report.data.City
import us.carleton.bat_weather_report.databinding.CityRowBinding
import us.carleton.bat_weather_report.network.WeatherResult

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>{

    private var cities = mutableListOf<City>(
        City("Ulaanbaatar"),
        City("Moscow"),
        City("London"),
        City("New York"),
        City("Beijing")
    )

    val context : Context
    constructor(context: Context) {
         this.context = context
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    private fun deleteCity(city: City) {
        val removedIndex = cities.indexOf(city)
        cities.remove(city)
        notifyItemRemoved(removedIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cityRowBinding = CityRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(cityRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = cities[holder.adapterPosition]
        holder.bind(currentCity)
    }

    fun addCity(city: City) {
        cities.add(city)
        notifyItemInserted(cities.lastIndex)
    }

    inner class ViewHolder(private val cityRowBinding: CityRowBinding) :
        RecyclerView.ViewHolder(cityRowBinding.root)
    {
            fun bind(city: City) {
                cityRowBinding.tvCityRow.text = city.name
                cityRowBinding.root.setOnClickListener{
                    val intent = Intent(context, WeatherActivity::class.java)
                    intent.putExtra("city", cityRowBinding.tvCityRow.text.toString())
                    context.startActivity(intent)
                }
                cityRowBinding.btnDelete.setOnClickListener {
                    deleteCity(city)
                }
            }
    }
}