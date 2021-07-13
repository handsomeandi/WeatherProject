package com.example.weatherproj.interactors

import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.weatherobjects.WeatherRepository
import javax.inject.Inject

class TownsInteractor @Inject constructor(
    var weatherRepository: WeatherRepository
) {
    fun addTown(town : TownClass){
        weatherRepository.addTown(town)
    }

    fun getAllTowns() : List<TownClass>{
        return weatherRepository.getAllTowns()
    }

    fun changeTown(town: TownClass) {
        weatherRepository.changeTown(town.name.toString())
    }
}