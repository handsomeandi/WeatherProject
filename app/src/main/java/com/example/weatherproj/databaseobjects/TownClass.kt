package com.example.weatherproj.databaseobjects

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherproj.utils.Constants

@Entity(tableName = Constants.TOWN_TABLE)
data class TownClass(
    @ColumnInfo(name = "name")
    val name: String?,
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)