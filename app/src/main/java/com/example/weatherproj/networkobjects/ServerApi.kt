package com.example.weatherproj.networkobjects

import com.example.weatherproj.Urls
import com.example.weatherproj.weatherobjects.Weather
import retrofit2.http.GET
import retrofit2.http.Query


interface ServerApi {
    @GET(Urls.URL_WEATHER)
    suspend fun getWeatherData( @Query("q") town:String): Weather?
    @GET(Urls.URL_WEATHER)
    suspend fun getCurrentLocationWeather(@Query("lat") lat:String,@Query("lon") lon:String) : Weather?
}