package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

class WindObject {
    @SerializedName("speed")
    private var speed: String? = null

    fun getSpeed(): String? {
        return speed
    }
}