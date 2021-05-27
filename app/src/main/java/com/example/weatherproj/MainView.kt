package com.example.weatherproj

import androidx.fragment.app.Fragment
import moxy.InjectViewState
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView : MvpView {

    fun changeFrag(id : Int)
}