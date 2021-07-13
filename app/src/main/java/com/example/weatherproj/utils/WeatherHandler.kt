package com.example.weatherproj.utils

import android.content.SharedPreferences
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.weatherobjects.Weather
import com.google.gson.Gson
import javax.inject.Inject

class WeatherHandler @Inject constructor(private val appSharedPreferences : SharedPreferences, private val gson: Gson, private val networkHandler: NetworkHandler) {

    private val editor = appSharedPreferences.edit()

    suspend fun getWeather(forceApiLoad : Boolean = false) : Weather?{
        return if(loadFromCoords()){
            loadFromCurrent().also {
                nextLoadFromList()
                saveWeather(it)
            }
        }else{
            loadFromData(appSharedPreferences,forceApiLoad)
        }
    }


    private suspend fun loadFromData(sharedPreferences: SharedPreferences?, forceApiLoad : Boolean = false) : Weather?{
        var weatherFromShared : String? = sharedPreferences?.getString(Constants.MY_WEATHER,"")
        var townNameFromShared : String = gson.fromJson(weatherFromShared, TownClass::class.java).name.toString()
        return if(weatherFromShared !=null && weatherFromShared.isNotEmpty() && townNameFromShared == getTown() && !forceApiLoad){
            loadFromShared(weatherFromShared)
        }else{
            val townName = getTown()
            if(townName != null){
                networkHandler.getWeatherData(townName).also{
                    saveWeather(it)
                }
            }else{
                null
            }
        }
    }

    private suspend fun loadFromCurrent() : Weather?{
        val lat = getLat()
        val lon = getLon()
        return if(lat!=null && lon != null){
            networkHandler.getCurrentLocationWeather(lat,lon)
        }else{
            null
        }
    }

    private fun loadFromShared(weatherFromShared: String): Weather {
        return gson.fromJson(weatherFromShared, Weather::class.java)
    }



    private fun loadFromCoords() : Boolean{
        return appSharedPreferences.getBoolean(Constants.LOAD_FROM_LOC,true)
    }

    private fun nextLoadFromList(){
        editor?.let{
            it.putBoolean(Constants.LOAD_FROM_LOC, false)
            it.apply()
        }
    }

    private fun getTown(): String? {
        return appSharedPreferences.getString(Constants.CURRENT_TOWN,"")
    }


    private fun getLon() : String?{
        return appSharedPreferences.getString(Constants.LONGITUDE,"")
    }

    private fun getLat() : String?{
        return appSharedPreferences.getString(Constants.LATITUDE,"")
    }

    private fun saveWeather(weather: Weather?) {
        editor?.let{
            it.remove(Constants.MY_WEATHER)
            it.putString(Constants.MY_WEATHER, gson.toJson(weather))
            it.apply()
        }
    }
}