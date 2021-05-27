package com.example.weatherproj.interactors

interface WeatherMvpInteractor {
    fun changeTown(town: String)
    fun getUrl() : String?
    fun getTown() : String?
}