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

    fun getAllTowns() : List<TownClass>{
        return dbInteractor.getAllTowns()
    }

    fun deleteAllTowns(){
        dbInteractor.deleteAllTowns()
    }

//    fun getTownByName(name : String) : TownClass?{
//        return dbInteractor.getTownByName(name)
//    }

    fun getAllTownNames() : List<String>{
        var names : ArrayList<String> = ArrayList()
        for (town in getAllTowns()){
            names.add(town.name.toString())
        }
        return names
    }



}