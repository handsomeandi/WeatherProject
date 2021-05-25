package com.example.weatherproj

import com.example.weatherproj.weatherobjects.Weather
import javax.inject.Inject


class WeatherInteractor @Inject constructor(
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

    fun loadDataFromApi() : Weather?{
        return mWeatherRepository.loadDataFromApi()
    }

    suspend fun getWeather() =  mWeatherRepository.getWeather()

    suspend fun getCurLocWeather() = mWeatherRepository.getCurLocWeather()


//    fun insertTown(town: TownClass){
//        mWeatherRepository.addTown(town)
//    }
}