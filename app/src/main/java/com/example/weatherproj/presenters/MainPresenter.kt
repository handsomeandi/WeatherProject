package com.example.weatherproj.presenters

import com.example.weatherproj.R
import com.example.weatherproj.utils.Screens
import com.example.weatherproj.views.MainView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject


@InjectViewState
class MainPresenter @Inject constructor (var router: Router) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.weatherScreen())
    }

    fun changeFrag(id:Int){
        var bottomNavId : Int
        var myScreen: Screen = when(id){
            R.id.weatherPage -> {
                bottomNavId = id
                Screens.weatherScreen()
            }
            R.id.townsPage -> {
                bottomNavId = id
                Screens.townsScreen()
            }
            R.id.infoPage -> {
                bottomNavId = id
                Screens.infoScreen()
            }
            else -> {
                bottomNavId = R.id.weatherPage
                Screens.weatherScreen()
            }

        }
        router.navigateTo(myScreen)
        viewState.setBottomNavigationItem(bottomNavId)
    }

}