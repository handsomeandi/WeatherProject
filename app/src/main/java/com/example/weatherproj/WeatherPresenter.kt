package com.example.weatherproj

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.networkobjects.DaggerNetworkComponent
import com.example.weatherproj.networkobjects.NetworkComponent
import com.example.weatherproj.networkobjects.NetworkModule
import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.weatherobjects.Weather
import com.google.gson.Gson
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@InjectViewState
class WeatherPresenter : MvpPresenter<WeatherView>() {

    var gson: Gson = Gson()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }




    fun loadDataFromApi() : Weather? {
        var component: NetworkComponent =

            DaggerNetworkComponent.builder().networkModule(NetworkModule()).build()
        var serverApi: ServerApi = component.getServerApi()
        var data: Weather? = null

        serverApi.getWeatherData()!!.enqueue(object : Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>?, response: Response<Weather?>) {
                if (response.isSuccessful()) {
                    data = response.body()
                    var savedWeatherData : String = gson.toJson(data)
                    viewState.saveWeather(savedWeatherData)
                    if(data != null){
                        viewState.showWeather(data!!)
                    }
                    Log.d("trackshared", "loaded from api")
                } else {
                    Log.d("ERROR", response.message())
                }
            }

            override fun onFailure(call: Call<Weather?>?, t: Throwable) {
                Log.d("ERROR", t.message!!)
            }
        })
        return data
        }

    fun loadFromSharedOrApi(sharedPreferences: SharedPreferences) {
        var weatherFromShared : String? = sharedPreferences.getString(
            Urls.MY_WEATHER,"")
        if(weatherFromShared!!.length > 0 && weatherFromShared != null && weatherFromShared != "null"){
            var weatherParsed : Weather = gson.fromJson(weatherFromShared, Weather::class.java)
            viewState.showWeather(weatherParsed)
            Log.d("trackshared", "loaded from shared")

        }else{
            loadDataFromApi()
        }
    }





}

