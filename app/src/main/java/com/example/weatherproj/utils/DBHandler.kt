package com.example.weatherproj.utils

import com.example.weatherproj.databaseobjects.TownClass

interface DBHandler {
    fun getAll(): List<TownClass>
    fun getTownByName(name:String?) : TownClass?
    fun insertAll(vararg users: TownClass)
    fun delete(user: TownClass)
    fun deleteAll()
}