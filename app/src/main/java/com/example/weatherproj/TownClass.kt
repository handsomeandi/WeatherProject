package com.example.weatherproj

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "town")
data class TownClass(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val name: String?
)