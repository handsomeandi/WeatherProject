package com.example.weatherproj.townsobjects

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface TownsView : MvpView{

    fun updateList(towns : List<String>)
    fun addToList(town : String)


}