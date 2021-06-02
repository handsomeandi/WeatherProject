package com.example.weatherproj.views

import com.example.weatherproj.databaseobjects.TownClass
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface TownsView : MvpView{

    fun updateList(towns : List<TownClass>)
    fun addItem(town: TownClass)
    fun switchToWeatherFrag()

}