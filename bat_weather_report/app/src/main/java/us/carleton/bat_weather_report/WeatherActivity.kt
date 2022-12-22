package us.carleton.bat_weather_report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.carleton.bat_weather_report.databinding.ActivityWeatherBinding
import us.carleton.bat_weather_report.network.WeatherAPI
import us.carleton.bat_weather_report.network.WeatherResult

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        var city : String? = "Moscow"
        if (extras != null) {
            city = extras.getString("city")
            //The key argument here must match that used in the other activity
        }
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            this.finish()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherAPI::class.java)

        val call = weatherService.getWeather(city!!, getString(R.string.imperial_unit_system), getString(R.string.appid))
        call.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                Glide.with(this@WeatherActivity)
                    .load(getString(R.string.image_url, response.body()!!.weather!![0].icon))
                    .override(500, 500)
                    .into(binding.ivWeatherIcon)
                binding.tvCityName.text = city
                binding.tvWeather.text = response.body()!!.weather!![0].description.toString().uppercase()
                binding.tvTemperature.text = response.body()!!.main!!.temp.toString() + "\u2109"
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                binding.tvWeather.text = "Error: ${t.message}"
            }
        })
    }
}