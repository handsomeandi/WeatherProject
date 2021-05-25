package com.example.weatherproj

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(TownClass::class), version = 2)
abstract class MyDB : RoomDatabase  {
    constructor() : super()
    abstract fun townDao(): TownDao

}