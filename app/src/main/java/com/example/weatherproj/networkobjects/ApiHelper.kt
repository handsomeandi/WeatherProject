package com.example.weatherproj.networkobjects

import com.example.weatherproj.MyApp
import javax.inject.Inject

class ApiHelper @Inject constructor(private val serverApi: ServerApi) {
    suspend fun getWeather() =
        serverApi.getWeatherData(MyApp.minstance!!.getUrl()!!, MyApp.minstance!!.getTown()!!)
    suspend fun getCurrLocWeather() = serverApi.getCurrentLocationWeather(
        MyApp.minstance!!.getUrl()!!,
        MyApp.minstance!!.getLat(),
        MyApp.minstance!!.getLon())

}