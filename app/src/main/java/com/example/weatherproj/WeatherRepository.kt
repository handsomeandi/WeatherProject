package com.example.weatherproj

class WeatherRepository{


    fun setWeatherTown(townName : String){
        MyApp().getInstance()!!.changeTown(townName)
    }

    fun getWeatherUrl() : String?{
        return MyApp().getInstance()!!.getUrl()
    }

    fun getWeatherTown() : String?{
        return MyApp().getInstance()!!.getTown()
    }

}