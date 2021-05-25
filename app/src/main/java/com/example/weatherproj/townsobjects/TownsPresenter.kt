package com.example.weatherproj.townsobjects

import com.example.weatherproj.*
import com.example.weatherproj.networkobjects.MainComponent
import com.example.weatherproj.networkobjects.NetworkModule
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class TownsPresenter @Inject constructor(private val weatherInteractor: WeatherInteractor, private val dbInteractor : DBInteractor) : MvpPresenter<TownsView>()  {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    fun changeTown(town : String){
        weatherInteractor.changeTown(town)
    }

    fun addTownToDB(town: TownClass){
        //MyApp.minstance!!.component!!.inject(this)

        dbInteractor.addTown(town)
    }



}