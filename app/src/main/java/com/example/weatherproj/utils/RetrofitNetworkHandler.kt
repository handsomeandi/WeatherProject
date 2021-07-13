package com.example.weatherproj.utils

import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.weatherobjects.Weather
import javax.inject.Inject

class RetrofitNetworkHandler @Inject constructor(private val serverApi: ServerApi)  : NetworkHandler {
    override suspend fun getWeatherData(town: String): Weather? = serverApi.getWeatherData(town)


    override suspend fun getCurrentLocationWeather(lat: String, lon: String): Weather? = serverApi.getCurrentLocationWeather(lat,lon)

}