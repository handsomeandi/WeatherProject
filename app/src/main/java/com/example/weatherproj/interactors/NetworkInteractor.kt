package com.example.weatherproj.interactors

import android.content.SharedPreferences
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.weatherobjects.Weather
import com.example.weatherproj.weatherobjects.WeatherRepository
import javax.inject.Inject


class NetworkInteractor @Inject constructor(
    var mWeatherRepository: WeatherRepository
){

    suspend fun getWeather(forceApiLoad:Boolean = false) : Weather? {
        return mWeatherRepository.getWeather(forceApiLoad)
    }

    fun changeTown(town: TownClass) {
        mWeatherRepository.changeTown(town.name.toString())
    }

}