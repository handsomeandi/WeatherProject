package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

data class WindObject(
    @SerializedName("speed")
    var speed: String? = null)