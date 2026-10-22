package com.example.weather_taztdinova

data class WeatherResponse(
    val name: String,
    val main: MainData,
    val wind: WindData
)

data class MainData(
    val temp: Double,
    val pressure: Int
)

data class WindData(
    val speed: Double
)
