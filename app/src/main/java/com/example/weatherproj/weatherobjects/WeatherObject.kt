package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

data class WeatherObject(
    @SerializedName("description")
    var description: String? = null)