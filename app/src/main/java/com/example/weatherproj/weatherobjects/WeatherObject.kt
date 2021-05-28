package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

class WeatherObject {
    @SerializedName("description")
    private var description: String? = null

    fun getDescription(): String? {
        return description
    }

}