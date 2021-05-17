package com.example.weatherproj.networkobjects

import com.example.weatherproj.Urls
import com.example.weatherproj.weatherobjects.Weather
import retrofit2.Call
import retrofit2.http.GET




interface ServerApi {
    @GET(Urls.WEATHER)
    fun getWeatherData(): Call<Weather?>?
}