package com.example.weatherproj.weatherobjects

import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.Urls
import com.example.weatherproj.WeatherInteractor
import com.example.weatherproj.WeatherRepository
import com.example.weatherproj.networkobjects.DaggerNetworkComponent
import com.example.weatherproj.networkobjects.NetworkComponent
import com.example.weatherproj.networkobjects.NetworkModule
import com.example.weatherproj.networkobjects.ServerApi
import com.google.gson.Gson
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@InjectViewState
class WeatherPresenter : MvpPresenter<WeatherView>() {

    var gson: Gson = Gson()

    var weatherInteractor : WeatherInteractor = WeatherInteractor(WeatherRepository())

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }


    fun loadDataFromApi(sharedPreferences: SharedPreferences) : Weather? {
        var component: NetworkComponent =
            DaggerNetworkComponent.builder().networkModule(NetworkModule()).build()
        var serverApi: ServerApi = component.getServerApi()
        var data: Weather? = null
        var url : String? = weatherInteractor.getUrl()

        if(url != null){
        serverApi.getWeatherData(url)!!.enqueue(object : Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>?, response: Response<Weather?>) {
                if (response.isSuccessful()) {
                    data = response.body()
                    if(data != null){
                        viewState.showWeather(data!!)
                        var savedWeatherData : String = gson.toJson(data)
                        saveWeather(savedWeatherData, sharedPreferences)
                    }
                    Log.d("trackshared", "loaded from api, url: " + weatherInteractor.getUrl())
                } else {
                    Log.d("ERROR", response.message())
                }
            }

            override fun onFailure(call: Call<Weather?>?, t: Throwable) {
                Log.d("ERROR", t.message!!)
            }
        })
        }
        return data
        }

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

