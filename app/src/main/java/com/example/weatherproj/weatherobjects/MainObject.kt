package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

class MainObject {
    @SerializedName("temp")
    private var temp: String? = null

    @SerializedName("humidity")
    private var humidity: String? = null


    fun getTemp(): String? {
        return temp
    }


    fun getHumidity(): String? {
        return humidity
    }

}