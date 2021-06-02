package com.example.weatherproj.weatherobjects

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.networkobjects.ServerApi
import com.google.gson.Gson
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    var serverApi : ServerApi,
    var dbDao: TownDao
){
    private val gson = Gson()
    private val appSharedPreferences = MainApp.instance!!.getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE)
    private val editor = appSharedPreferences.edit()


    suspend fun getWeather(forceApiLoad : Boolean = false) : Weather?{
        if(loadFromCoords()){
            nextLoadFromList()
            saveWeather(loadFromCurrent())
            return loadFromCurrent()
        }else{
            return loadFromData(getShared(), forceApiLoad)
        }
    }


    private suspend fun loadFromData(sharedPreferences: SharedPreferences, forceApiLoad : Boolean = false) : Weather?{
        var weatherFromShared : String? = sharedPreferences.getString(Constants.MY_WEATHER,"")
        var townNameFromShared : String = gson.fromJson(weatherFromShared,TownClass::class.java).name.toString()
        if(weatherFromShared !=null && weatherFromShared.isNotEmpty() && townNameFromShared == getTown() && !forceApiLoad){
            return loadFromShared(weatherFromShared)
        }else{
            saveWeather(serverApi.getWeatherData(getTown()!!))
            return serverApi.getWeatherData(getTown()!!)
        }
    }

    private fun getShared() : SharedPreferences{
        return appSharedPreferences
    }

    private suspend fun loadFromCurrent() : Weather?{
        return serverApi.getCurrentLocationWeather(getLat()!!,getLon()!!)
    }

    private fun loadFromShared(weatherFromShared: String): Weather {
        return gson.fromJson(weatherFromShared, Weather::class.java)
    }

    fun addTown(town : TownClass){
        for (checkTown in dbDao.getAll()){
            if(checkTown.name == town.name || town.name.isNullOrBlank()){
                return
            }
        }
        dbDao.insertAll(town)
    }

    fun getAllTowns() : List<TownClass>{
        return dbDao.getAll()
    }

    private fun loadFromCoords() : Boolean{
        return appSharedPreferences.getBoolean(Constants.LOAD_FROM_LOC,true)
    }

    private fun nextLoadFromList(){
        editor.putBoolean(Constants.LOAD_FROM_LOC, false)
        editor.apply()
    }

    private fun getTown(): String? {
        return appSharedPreferences.getString(Constants.CURRENT_TOWN,"")
    }

    fun changeTown(newTown: String) {
        editor.putString(Constants.CURRENT_TOWN, newTown)
        editor.apply()
    }

    private fun getLon() : String?{
        return appSharedPreferences.getString(Constants.LONGITUDE,"")
    }

    private fun getLat() : String?{
        return appSharedPreferences.getString(Constants.LATITUDE,"")
    }

    fun saveWeather(weather: Weather?) {
        editor.remove(Constants.MY_WEATHER)
        editor.putString(Constants.MY_WEATHER, gson.toJson(weather))
        editor.apply()
    }


}