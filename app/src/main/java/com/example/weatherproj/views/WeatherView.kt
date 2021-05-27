package com.example.weatherproj.views

import com.example.weatherproj.weatherobjects.Weather
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(value = AddToEndStrategy::class)
interface WeatherView : MvpView {
    fun showWeather(weather : Weather)
}