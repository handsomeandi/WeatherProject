package com.example.weatherproj.interactors

import com.example.weatherproj.weatherobjects.WeatherRepository
import javax.inject.Inject


class NetworkInteractor @Inject constructor(
    var mWeatherRepository: WeatherRepository
) : WeatherMvpInteractor {



    override fun changeTown(town: String) {
        mWeatherRepository.setWeatherTown(town)
    }

    override fun getUrl() : String? {
        return mWeatherRepository.getWeatherUrl()
    }

    override fun getTown(): String {
        return mWeatherRepository.getWeatherTown()
    }

    suspend fun getWeather() =  mWeatherRepository.getWeather()

    suspend fun getCurLocWeather() = mWeatherRepository.getCurLocWeather()

}