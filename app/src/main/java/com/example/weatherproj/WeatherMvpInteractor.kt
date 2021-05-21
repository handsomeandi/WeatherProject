package com.example.weatherproj

interface WeatherMvpInteractor {
    fun changeTown(town: String)
    fun getUrl() : String?
    fun getTown() : String?
}