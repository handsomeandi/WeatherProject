package com.example.weatherproj.databaseobjects

import androidx.room.*
import com.example.weatherproj.Urls
import com.example.weatherproj.databaseobjects.TownClass
import retrofit2.http.Url


@Dao
interface TownDao {
    @Query("SELECT * FROM " + Urls.TOWN_TABLE)
    fun getAll(): List<TownClass>


    @Query("SELECT * FROM " + Urls.TOWN_TABLE + " WHERE name = :name")
    fun getTownByName(name:String?) : TownClass?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: TownClass)

    @Delete
    fun delete(user: TownClass)

    @Query("DELETE FROM " + Urls.TOWN_TABLE)
    fun deleteAll()

}