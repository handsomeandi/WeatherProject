package com.example.weatherproj.databaseobjects

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TownClass::class], version = 2)
abstract class MyDB : RoomDatabase  {
    constructor() : super()
    abstract fun townDao(): TownDao

}