package com.example.weatherproj

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "town")
data class TownClass(
    @ColumnInfo(name = "name") val name: String?,
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)