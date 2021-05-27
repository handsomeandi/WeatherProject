package com.example.weatherproj.weatherobjects

import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.*
import com.example.weatherproj.networkobjects.MainComponent
import com.example.weatherproj.networkobjects.NetworkModule
import com.example.weatherproj.networkobjects.ServerApi
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import androidx.lifecycle.liveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@InjectViewState
class WeatherPresenter @Inject constructor(var weatherInteractor : WeatherInteractor, var dbInteractor: DBInteractor) : MvpPresenter<WeatherView>() {

//    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())


    var gson: Gson = Gson()
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())



    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }


    fun loadDataFromApi() =
        liveData(Dispatchers.IO) {
            var data: Weather?
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = weatherInteractor.getWeather()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
            }
            //  MyApp.minstance!!.component!!.inject(this)
//        scope.launch {
//            val data  =  weatherInteractor.loadDataFromApi()
//            viewState.showWeather(data!!)
//            var savedWeatherData : String = gson.toJson(data!!)
//            saveWeather(savedWeatherData, sharedPreferences)
//        }
        }



    fun deleteAllTowns(){
        dbInteractor.deleteAllTowns()
    }

    fun loadCurlocWeatherFromApi() =
        liveData(Dispatchers.IO) {
            var data: Weather?
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = weatherInteractor.getCurLocWeather()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
            }
            //  MyApp.minstance!!.component!!.inject(this)
//        scope.launch {
//            val data  =  weatherInteractor.loadDataFromApi()
//            viewState.showWeather(data!!)
//            var savedWeatherData : String = gson.toJson(data!!)
//            saveWeather(savedWeatherData, sharedPreferences)
//        }
        }
//    suspend fun getApiData() : Weather?{
//        return weatherInteractor.loadDataFromApi()
//    }

    fun loadFromShared(weatherFromShared: String) {
        var weatherParsed : Weather = gson.fromJson(weatherFromShared, Weather::class.java)
        viewState.showWeather(weatherParsed)
        Log.d("trackshared", "loaded from shared")

    }

    fun addTownToDb(town: TownClass){
        dbInteractor.addTown(town)
    }


    fun getAllTowns() : List<TownClass>{
        return dbInteractor.getAllTowns()
    }

    fun getTownByName(name : String) : TownClass?{
        return dbInteractor.getTownByName(name)
    }
//    fun saveWeather(weather : String, sharedPreferences: SharedPreferences){
//        val editor = sharedPreferences.edit()
//        editor.remove(Urls.MY_WEATHER)
//        editor.putString(Urls.MY_WEATHER, weather)
//        editor.commit()
//
//        Log.d("SharedPrefs", sharedPreferences.getString(Urls.MY_WEATHER, "")!!)
//    }
    fun removeWeather(sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.commit()
    }
}

