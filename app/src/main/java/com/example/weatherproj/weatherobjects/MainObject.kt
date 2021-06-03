package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

data class MainObject(
    @SerializedName("temp")
    var temp: String? = null,

    @SerializedName("humidity")
    var humidity: String? = null
)