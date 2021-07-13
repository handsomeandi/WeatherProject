package com.example.weatherproj.presenters

import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.interactors.TownsInteractor
import com.example.weatherproj.views.TownsView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class TownsPresenter @Inject constructor(private val townsInteractor : TownsInteractor) : MvpPresenter<TownsView>()  {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onTownsRequired()
    }

    fun onAddButtonClick(town:TownClass){
        townsInteractor.addTown(town)
        viewState.addItem(town)
    }

    private fun onTownsRequired(){
        var towns: List<TownClass> = townsInteractor.getAllTowns()
        onTownsReceived(towns)
    }

    private fun onTownsReceived(towns:List<TownClass>){
        viewState.updateList(towns)
    }

    fun onTownClicked(town: TownClass){
        townsInteractor.changeTown(town)
        viewState.switchToWeatherFrag()
    }
}