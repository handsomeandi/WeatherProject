package com.example.weatherproj.weatherobjects

import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.*
import com.example.weatherproj.networkobjects.DaggerMainComponent
import com.example.weatherproj.networkobjects.MainComponent
import com.example.weatherproj.networkobjects.NetworkModule
import com.example.weatherproj.networkobjects.ServerApi
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@InjectViewState
class WeatherPresenter @Inject constructor(private val weatherInteractor : WeatherInteractor) : MvpPresenter<WeatherView>() {

//    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())


    var gson: Gson = Gson()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }


    fun loadDataFromApi(sharedPreferences: SharedPreferences){
      //  MyApp.minstance!!.component!!.inject(this)
        val data  =  weatherInteractor.loadDataFromApi()
        viewState.showWeather(data!!)
        var savedWeatherData : String = gson.toJson(data!!)
        saveWeather(savedWeatherData, sharedPreferences)
    }

//    suspend fun getApiData() : Weather?{
//        return weatherInteractor.loadDataFromApi()
//    }

    fun loadFromShared(weatherFromShared: String) {
        var weatherParsed : Weather = gson.fromJson(weatherFromShared, Weather::class.java)
        viewState.showWeather(weatherParsed)
        Log.d("trackshared", "loaded from shared")

    }

    fun saveWeather(weather : String, sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.putString(Urls.MY_WEATHER, weather)
        editor.commit()

        Log.d("SharedPrefs", sharedPreferences.getString(Urls.MY_WEATHER, "")!!)
    }
    fun removeWeather(sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.commit()
    }
}

