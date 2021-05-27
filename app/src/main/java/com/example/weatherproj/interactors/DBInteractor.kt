package com.example.weatherproj.interactors

import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.weatherobjects.WeatherRepository
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