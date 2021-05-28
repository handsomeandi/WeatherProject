package com.example.weatherproj.weatherobjects

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.MainApp
import com.example.weatherproj.Urls
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.networkobjects.ServerApi
import com.google.gson.Gson
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    var serverApi : ServerApi,
    var dbDao: TownDao
){
    val gson = Gson()
    val appSharedPreferences = MainApp.instance!!.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)
    val editor = appSharedPreferences.edit()


    suspend fun gettWeaather(sharedPreferences: SharedPreferences, forceApiLoad : Boolean = false) : Weather?{
        if(loadFromCoords()){
            nextLoadFromList()
            return loadFromCurrent()
        }else{
            return loadFromData(sharedPreferences, forceApiLoad)
        }
    }


    private suspend fun loadFromData(sharedPreferences: SharedPreferences, forceApiLoad : Boolean = false) : Weather?{
        var weatherFromShared : String? = sharedPreferences.getString(Urls.MY_WEATHER,"")
        var townNameFromShared : String = gson.fromJson(weatherFromShared,TownClass::class.java).name.toString()
        if(weatherFromShared !=null && weatherFromShared.isNotEmpty() && townNameFromShared == getTown() && !forceApiLoad){
            Log.d("track", "loaded from shared")
            return loadFromShared(weatherFromShared)
        }else{
            return serverApi.getWeatherData(getTown()!!)
        }
    }

    private suspend fun loadFromCurrent() : Weather?{
        return serverApi.getCurrentLocationWeather(getLat()!!,getLon()!!)
    }

    fun loadFromShared(weatherFromShared: String): Weather {
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

    fun getTownByName(name : String) : TownClass?{
        return dbDao.getTownByName(name)
    }

    fun deleteAllTowns(){
        dbDao.deleteAll()
    }


    fun loadFromCoords() : Boolean{
        val kek = appSharedPreferences.getBoolean(Urls.LOAD_FROM_LOC,true)
        return appSharedPreferences.getBoolean(Urls.LOAD_FROM_LOC,true)
    }

    fun nextLoadFromList(){
        editor.putBoolean(Urls.LOAD_FROM_LOC, false)
        editor.apply()
    }

    fun getTown(): String? {
        val townchek = appSharedPreferences.getString(Urls.CURRENT_TOWN,"")
        return appSharedPreferences.getString(Urls.CURRENT_TOWN,"")
    }

    fun changeTown(newTown: String) {
        editor.putString(Urls.CURRENT_TOWN, newTown)
        editor.apply()
    }

    fun getLon() : String?{
        return appSharedPreferences.getString(Urls.LONGITUDE,"")
    }

    fun getLat() : String?{
        return appSharedPreferences.getString(Urls.LATITUDE,"")
    }


}