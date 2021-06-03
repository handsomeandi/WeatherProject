package com.example.weatherproj.weatherobjects

import com.google.gson.annotations.SerializedName

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;



data class Weather(

    @SerializedName("main")
    private val main: MainObject? = null,

    @SerializedName("weather")
    private val weather: List<WeatherObject>? = null,

    @SerializedName("wind")
    private val wind: WindObject? = null,

    @SerializedName("sys")
    private  var sys: SunObject,

    @SerializedName("name")
    var townName : String? = null
) {

    fun getTemp(): String? {
        return main!!.temp
    }

    fun getWeatherConditions(): String? {
        return weather!![0].description
    }

    fun getWindSpeed(): String? {
        return wind!!.speed
    }


    fun getHumidity(): String? {
        return main!!.humidity
    }


    fun getSunrise(): String? {
        return getDate(sys.sunrise, "HH:mm:ss")
    }


    fun getSunset(): String? {
        return getDate(sys.sunset, "HH:mm:ss")
    }

    fun getDate(seconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds * 1000
        return formatter.format(calendar.time)
    }



}