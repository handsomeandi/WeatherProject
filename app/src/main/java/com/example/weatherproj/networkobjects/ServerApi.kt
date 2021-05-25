package com.example.weatherproj.networkobjects

import com.example.weatherproj.Urls
import com.example.weatherproj.weatherobjects.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ServerApi {
    @GET()
    suspend fun getWeatherData( @Url url:String, @Query("q") town:String): Weather?
}