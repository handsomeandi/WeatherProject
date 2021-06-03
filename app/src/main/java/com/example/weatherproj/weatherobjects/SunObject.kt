package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

data class SunObject(

    @SerializedName("sunrise")
    var sunrise: Long = 0,

    @SerializedName("sunset")
    var sunset: Long = 0
)