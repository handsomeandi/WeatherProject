package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;



class Weather {
    @SerializedName("main")
    private val main: MainObject? = null

    @SerializedName("weather")
    private val weather: List<WeatherObject>? = null

    @SerializedName("wind")
    private val wind: WindObject? = null

    @SerializedName("sys")
    private lateinit var sys: SunObject

    fun getTemp(): String? {
        return main!!.getTemp()
    }

    fun getWeatherConditions(): String? {
        return weather!![0].getDescription()
    }

    fun getWindSpeed(): String? {
        return wind!!.getSpeed()
    }


    fun getHumidity(): String? {
        return main!!.getHumidity()
    }


    fun getSunrise(): String? {
        return getDate(sys.getSunrise(), "HH:mm:ss")
    }


    fun getSunset(): String? {
        return getDate(sys.getSunset(), "HH:mm:ss")
    }

    fun getDate(seconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(seconds * 1000)
        return formatter.format(calendar.getTime())
    }


}