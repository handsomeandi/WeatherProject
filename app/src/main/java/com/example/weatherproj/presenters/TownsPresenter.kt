package com.example.weatherproj.presenters

import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.interactors.DBInteractor
import com.example.weatherproj.interactors.NetworkInteractor
import com.example.weatherproj.views.TownsView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class TownsPresenter @Inject constructor(private val networkInteractor: NetworkInteractor, private val dbInteractor : DBInteractor) : MvpPresenter<TownsView>()  {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    fun changeTown(town : String){
        networkInteractor.changeTown(town)
    }

    fun addTownToDB(town: TownClass){
        dbInteractor.addTown(town)
    }

    fun getAllTowns() : List<TownClass>{
        return dbInteractor.getAllTowns()
    }

    fun deleteAllTowns(){
        dbInteractor.deleteAllTowns()
    }

    fun getAllTownNames() : List<String>{
        var names : ArrayList<String> = ArrayList()
        for (town in getAllTowns()){
            names.add(town.name.toString())
        }
        return names
    }



}