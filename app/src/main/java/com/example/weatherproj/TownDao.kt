package com.example.weatherproj

import androidx.room.*


@Dao
interface TownDao {
    @Query("SELECT * FROM town")
    fun getAll(): List<TownClass>

//    @Query("SELECT * FROM town WHERE id IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<TownClass>
//
//    @Query("SELECT * FROM town WHERE id LIKE :id LIMIT 1")
//    fun findById(id: Int): TownClass

    @Query("SELECT * FROM town WHERE name = :name")
    fun getTownByName(name:String?) : TownClass?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: TownClass)

    @Delete
    fun delete(user: TownClass)

    @Query("DELETE FROM town")
    fun deleteAll()

}