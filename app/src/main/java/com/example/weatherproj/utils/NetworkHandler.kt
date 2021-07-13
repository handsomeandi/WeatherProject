package com.example.weatherproj.utils

import com.example.weatherproj.weatherobjects.Weather
import retrofit2.http.Query

interface NetworkHandler {
    suspend fun getWeatherData( town:String): Weather?
    suspend fun getCurrentLocationWeather(lat:String, lon:String) : Weather?
}