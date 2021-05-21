package com.example.weatherproj

import javax.inject.Inject

class DBInteractor @Inject constructor(
    var mWeatherRepository: WeatherRepository
) {
    fun addTown(town : TownClass){
        mWeatherRepository.addTown(town)
    }
}