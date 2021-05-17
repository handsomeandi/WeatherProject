package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

class SunObject{
    @SerializedName("sunrise")
    private var sunrise: Long = 0

    @SerializedName("sunset")
    private var sunset: Long = 0

    fun getSunset(): Long {
        return sunset
    }

    fun setSunset(sunset: Long) {
        this.sunset = sunset
    }

    fun getSunrise(): Long {
        return sunrise
    }

    fun setSunrise(sunrise: Long) {
        this.sunrise = sunrise
    }
}