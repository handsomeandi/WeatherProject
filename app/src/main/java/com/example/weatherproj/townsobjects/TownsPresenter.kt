package com.example.weatherproj.townsobjects

import com.example.weatherproj.WeatherInteractor
import com.example.weatherproj.WeatherRepository
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class TownsPresenter : MvpPresenter<TownsView>() {


    var weatherInteractor : WeatherInteractor = WeatherInteractor(WeatherRepository())

    fun addTownToDB(town : String){

    }

    fun changeTown(town : String){
        weatherInteractor.changeTown(town)
    }



}