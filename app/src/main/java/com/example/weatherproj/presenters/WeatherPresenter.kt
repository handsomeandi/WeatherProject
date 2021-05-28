package com.example.weatherproj.presenters

import android.content.SharedPreferences
import com.google.gson.Gson
import moxy.InjectViewState
import moxy.MvpPresenter
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.interactors.DBInteractor
import com.example.weatherproj.interactors.NetworkInteractor
import com.example.weatherproj.views.WeatherView
import com.example.weatherproj.weatherobjects.Weather
import kotlinx.coroutines.*
import javax.inject.Inject


@InjectViewState
class WeatherPresenter @Inject constructor(var networkInteractor : NetworkInteractor, var dbInteractor: DBInteractor) : MvpPresenter<WeatherView>() {

    var gson: Gson = Gson()

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())


    fun onWeatherRequired(sharedPreferences: SharedPreferences,forceApiLoad : Boolean = false){
        scope.launch {
            val data = async(context = Dispatchers.IO){networkInteractor.getWeatherTest(sharedPreferences,forceApiLoad)}
            withContext(Dispatchers.Main) {onWeatherReceived(data.await())}
        }
    }

    private fun onWeatherReceived(weather: Weather?){
        if(weather!=null){
            viewState.showWeather(weather)
            dbInteractor.addTown(TownClass(weather.getTownName()))
            networkInteractor.changeTown(TownClass(weather.getTownName()))
            viewState.saveWeather(weather)
        }
    }























//    fun loadDataFromApi() =
//        liveData(Dispatchers.IO) {
//            var data: Weather?
//            emit(Resource.loading(data = null))
//            try {
//                emit(Resource.success(data = networkInteractor.getWeather()))
//            } catch (exception: Exception) {
//                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
//            }
//        }
//
//
//    fun deleteAllTowns(){
//        dbInteractor.deleteAllTowns()
//    }
//
//    fun loadCurlocWeatherFromApi() =
//        liveData(Dispatchers.IO) {
//            var data: Weather?
//            emit(Resource.loading(data = null))
//            try {
//                emit(Resource.success(data = networkInteractor.getCurLocWeather()))
//            } catch (exception: Exception) {
//                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
//            }
//        }
//
//    fun loadFromShared(weatherFromShared: String) {
//        var weatherParsed : Weather = gson.fromJson(weatherFromShared, Weather::class.java)
//        viewState.showWeather(weatherParsed)
//        Log.d("trackshared", "loaded from shared")
//    }
//    fun addTownToDb(town: TownClass){
//        dbInteractor.addTown(town)
//    }
//
//    fun getAllTowns() : List<TownClass>{
//        return dbInteractor.getAllTowns()
//    }
//
//    fun getTownByName(name : String) : TownClass?{
//        return dbInteractor.getTownByName(name)
//    }
//
//    fun removeWeather(sharedPreferences: SharedPreferences){
//        val editor = sharedPreferences.edit()
//        editor.remove(Urls.MY_WEATHER)
//        editor.commit()
//    }
}
