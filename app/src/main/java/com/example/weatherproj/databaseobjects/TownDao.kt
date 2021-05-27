package com.example.weatherproj.databaseobjects

import androidx.room.*
import com.example.weatherproj.databaseobjects.TownClass


@Dao
interface TownDao {
    @Query("SELECT * FROM town")
    fun getAll(): List<TownClass>


    @Query("SELECT * FROM town WHERE name = :name")
    fun getTownByName(name:String?) : TownClass?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: TownClass)

    @Delete
    fun delete(user: TownClass)

    @Query("DELETE FROM town")
    fun deleteAll()

}