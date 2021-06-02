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



    fun onAddButtonClick(town:TownClass){
        dbInteractor.addTown(town)
        viewState.addItem(town)
    }

    fun onTownsRequired(){
        var towns: List<TownClass> = dbInteractor.getAllTowns()
        onTownsReceived(towns)
    }

    private fun onTownsReceived(towns:List<TownClass>){
        viewState.updateList(towns)
    }

    fun onTownClicked(town: TownClass){
        networkInteractor.changeTown(town)
        viewState.switchToWeatherFrag()
    }
}