package com.example.weatherproj.interactors

import com.example.weatherproj.weatherobjects.Weather
import com.example.weatherproj.weatherobjects.WeatherRepository
import javax.inject.Inject


class WeatherInteractor @Inject constructor(
    private var weatherRepository: WeatherRepository
){

    suspend fun getWeather(forceApiLoad:Boolean = false) : Weather? {
        return weatherRepository.getWeather(forceApiLoad)
    }

}