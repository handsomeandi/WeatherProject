package com.example.weatherproj

import com.example.weatherproj.weatherobjects.Weather
import javax.inject.Inject


class WeatherInteractor @Inject constructor(
    private val mWeatherRepository: WeatherRepository
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

    fun loadDataFromApi() : Weather?{
        return mWeatherRepository.loadDataFromApi()
    }

//    fun insertTown(town: TownClass){
//        mWeatherRepository.addTown(town)
//    }
}