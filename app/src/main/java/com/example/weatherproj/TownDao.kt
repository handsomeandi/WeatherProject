package com.example.weatherproj

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TownDao {
    @Query("SELECT * FROM town")
    fun getAll(): List<TownClass>

    @Query("SELECT * FROM town WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<TownClass>

    @Query("SELECT * FROM town WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): TownClass

    @Insert
    fun insertAll(vararg users: TownClass)

    @Delete
    fun delete(user: TownClass)

}