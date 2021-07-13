package com.example.weatherproj.utils

import android.content.SharedPreferences
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databaseobjects.TownDao
import javax.inject.Inject

class RoomDbHandler @Inject constructor(private val townDao: TownDao) : DBHandler {
    override fun getAll(): List<TownClass> = townDao.getAll()


    override fun getTownByName(name: String?): TownClass? = townDao.getTownByName(name)

    override fun insertAll(vararg users: TownClass) = townDao.insertAll(*users)

    override fun delete(user: TownClass) = townDao.delete(user)


    override fun deleteAll() = townDao.deleteAll()
}