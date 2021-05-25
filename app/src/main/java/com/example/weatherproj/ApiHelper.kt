package com.example.weatherproj

import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.weatherobjects.Weather
import javax.inject.Inject

class ApiHelper @Inject constructor(private val serverApi: ServerApi) {
    suspend fun getWeather() =
        serverApi.getWeatherData(MyApp.minstance!!.getUrl()!!,MyApp.minstance!!.getTown()!!)

}