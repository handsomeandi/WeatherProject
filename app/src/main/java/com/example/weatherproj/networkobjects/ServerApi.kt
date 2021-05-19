package com.example.weatherproj.networkobjects

import com.example.weatherproj.Urls
import com.example.weatherproj.weatherobjects.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface ServerApi {
    @GET()
    fun getWeatherData(@Url url:String): Call<Weather?>?
}