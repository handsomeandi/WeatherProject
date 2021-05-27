package com.example.weatherproj

import javax.inject.Inject

class DBInteractor @Inject constructor(
    var mWeatherRepository: WeatherRepository
) {
    fun addTown(town : TownClass){
        mWeatherRepository.addTown(town)
    }

    fun getAllTowns() : List<TownClass>{
        return mWeatherRepository.getAllTowns()
    }

    fun getTownByName(townName : String) : TownClass?{
        return mWeatherRepository.getTownByName(townName)
    }

    fun deleteAllTowns(){
        mWeatherRepository.deleteAllTowns()
    }
}