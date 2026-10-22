package com.example.weather_taztdinova

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val key = "33402c7548e4d40268ef7970d18e9f30"

    private lateinit var etCity: AppCompatEditText
    private lateinit var tvCity: AppCompatTextView
    private lateinit var tvTemp: AppCompatTextView
    private lateinit var tvPressure: AppCompatTextView
    private lateinit var tvWind: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.et_city)
        tvCity = findViewById(R.id.tv_city)
        tvTemp = findViewById(R.id.tv_temp)
        tvPressure = findViewById(R.id.tv_pressure)
        tvWind = findViewById(R.id.tv_wind)
        val btSearch: AppCompatButton = findViewById(R.id.bt_search)

        btSearch.setOnClickListener {
            val city = etCity.text.toString().trim()
            if (city.isEmpty()) {
                Toast.makeText(this, R.string.empty_city, Toast.LENGTH_SHORT).show()
            } else {
                loadWeather(city)
            }
        }
    }

    private fun loadWeather(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + key + "&units=metric&lang=ru"

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.d("MyLog", "Response: $response")
                showWeather(response)
            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
                Toast.makeText(this, R.string.error_city, Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun showWeather(json: String) {
        val weather = Gson().fromJson(json, WeatherResponse::class.java)

        Log.d("MyLog", "Город: ${weather.name}, температура: ${weather.main.temp}")

        tvCity.text = weather.name
        tvTemp.text = weather.main.temp.toString()
        tvPressure.text = weather.main.pressure.toString()
        tvWind.text = weather.wind.speed.toString()
    }
}
