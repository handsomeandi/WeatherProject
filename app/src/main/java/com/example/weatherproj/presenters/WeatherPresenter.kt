package com.example.weatherproj.presenters

import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.interactors.TownsInteractor
import com.example.weatherproj.interactors.WeatherInteractor
import com.example.weatherproj.views.WeatherView
import com.example.weatherproj.weatherobjects.Weather
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject


@InjectViewState
class WeatherPresenter @Inject constructor(var weatherInteractor : WeatherInteractor, var townsInteractor: TownsInteractor) : MvpPresenter<WeatherView>() {

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())


    fun onWeatherRequired(forceApiLoad: Boolean = false) {
        scope.launch {
            val data =
                async(context = Dispatchers.IO) { weatherInteractor.getWeather(forceApiLoad) }
            withContext(Dispatchers.Main) { onWeatherReceived(data.await()) }
        }
    }

    private fun onWeatherReceived(weather: Weather?) {
        if (weather != null) {
            viewState.showWeather(weather)
            townsInteractor.addTown(TownClass(weather.townName))
            townsInteractor.changeTown(TownClass(weather.townName))
        }
    }
}

