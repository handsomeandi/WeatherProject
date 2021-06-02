package com.example.weatherproj.databaseobjects

import androidx.room.*
import com.example.weatherproj.utils.Constants


@Dao
interface TownDao {
    @Query("SELECT * FROM " + Constants.TOWN_TABLE)
    fun getAll(): List<TownClass>


    @Query("SELECT * FROM " + Constants.TOWN_TABLE + " WHERE name = :name")
    fun getTownByName(name:String?) : TownClass?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: TownClass)

    @Delete
    fun delete(user: TownClass)

    @Query("DELETE FROM " + Constants.TOWN_TABLE)
    fun deleteAll()

}