package com.example.weatherproj.utils

import android.content.SharedPreferences
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.weatherobjects.Weather
import javax.inject.Inject

class TownHandler @Inject constructor(private val dbHandler: DBHandler, sharedPreferences: SharedPreferences) {

    val editor = sharedPreferences.edit()

    fun addTown(town : TownClass){
        for (checkTown in dbHandler.getAll()){
            if(checkTown.name == town.name || town.name.isNullOrBlank()){
                return
            }
        }
        dbHandler.insertAll(town)
    }

    fun getAllTowns() : List<TownClass>{
        return dbHandler.getAll()
    }

    fun changeTown(newTown: String) {
        editor?.let{
            it.putString(Constants.CURRENT_TOWN, newTown)
            it.apply()
        }
    }


}