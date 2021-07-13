package com.example.weatherproj.weatherobjects

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.utils.TownHandler
import com.example.weatherproj.utils.WeatherHandler
import com.google.gson.Gson
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherHandler: WeatherHandler, private val townHandler: TownHandler
){
   suspend fun getWeather(forceApiLoad:Boolean) = weatherHandler.getWeather(forceApiLoad)

    fun addTown(town:TownClass) = townHandler.addTown(town)

    fun getAllTowns() : List<TownClass> = townHandler.getAllTowns()

    fun changeTown(newTown: String) = townHandler.changeTown(newTown)
}