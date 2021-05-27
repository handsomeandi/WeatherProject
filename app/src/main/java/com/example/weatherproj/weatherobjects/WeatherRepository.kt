package com.example.weatherproj.weatherobjects

import com.example.weatherproj.networkobjects.ApiHelper
import com.example.weatherproj.MyApp
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.networkobjects.ServerApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    var serverApi : ServerApi,
    var dbDao: TownDao,
    var apiHelper: ApiHelper
){

    fun setWeatherTown(townName : String){
        MyApp.minstance!!.changeTown(townName)
    }

    fun getWeatherUrl() : String?{
        return MyApp.minstance!!.getUrl()
    }

    fun getWeatherTown() : String{
        return MyApp.minstance!!.getTown()
    }


    suspend fun getWeather() = apiHelper.getWeather()

    suspend fun getCurLocWeather() = apiHelper.getCurrLocWeather()

    fun addTown(town : TownClass){
        for (checkTown in dbDao.getAll()){
            if(checkTown.name == town.name){
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

}