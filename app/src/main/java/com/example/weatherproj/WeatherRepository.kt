package com.example.weatherproj

import android.util.Log
import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.weatherobjects.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    var serverApi : ServerApi,
    var dbDao: TownDao,
    var apiHelper: ApiHelper
){




    fun setWeatherTown(townName : String){
        MyApp.minstance!!.changeTown(townName)
    }

    fun getWeatherUrl() : String?{
        return MyApp.minstance!!.getUrl()
    }

    fun getWeatherTown() : String{
        return MyApp.minstance!!.getTown()
    }

    fun loadDataFromApi() : Weather?{
        var data: Weather? = null
        var url : String? = getWeatherUrl()
        var town : String = getWeatherTown()

//        if(url != null){
//            serverApi.getWeatherData(url, town)!!.enqueue(object : Callback<Weather?> {
//                override fun onResponse(call: Call<Weather?>?, response: Response<Weather?>) {
//                    if (response.isSuccessful()) {
//                        data = response.body()
//                        Log.d("trackshared", "loaded from api, url: " + getWeatherUrl())
//                    } else {
//                        Log.d("ERROR", response.message())
//                    }
//                }
//                override fun onFailure(call: Call<Weather?>?, t: Throwable) {
//                    Log.d("ERROR", t.message!!)
//                }
//            })
//        }
        return data
    }

    suspend fun getWeather() = apiHelper.getWeather()

    fun getWeatherApiHelper() : ApiHelper{
        return apiHelper
    }

    fun addTown(town : TownClass){
        dbDao.insertAll(town)
        Log.d("db", town.name!!)
    }

    fun getAllTowns() : List<TownClass>{
        return dbDao.getAll()
    }

    fun deleteAllTowns(){
        dbDao.deleteAll()
    }

}