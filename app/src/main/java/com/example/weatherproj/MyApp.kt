package com.example.weatherproj

import android.app.Application
import com.example.weatherproj.networkobjects.DaggerMainComponent
import com.example.weatherproj.networkobjects.MainComponent
import com.example.weatherproj.networkobjects.NetworkModule


class MyApp() : Application() {

    private var mtown: String = "Simferopol"
    private var myUrl: String =
        "weather?q=town&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
    private var curLocUrl: String = "http://api.openweathermap.org/data/2.5/weather?lat=smh&lon=smh&appid=53c6e39cf3ee11a1d7549ffea83d6bd8"
    private var lat : String = "0"
    private var lon : String = "0"
    private var loadFromCoordsBool = true

    var component: MainComponent? = null

    companion object {
        var minstance: MyApp? = null
        var myDatabase: MyDB? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        minstance = this
        // minstance = this
        mtown = "Simferopol"
        myUrl = "weather?q=$mtown&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
        component = DaggerMainComponent.builder().contextModule(ContextModule(this)).dBModule(DBModule()).networkModule(NetworkModule()).build()
        /* myDatabase = Room.databaseBuilder(component.getContext(), MyDB::class.java, "WeatherDB").allowMainThreadQueries()
                 .fallbackToDestructiveMigration()
                 .build()*/


    }

    fun loadFromCoords() : Boolean{
        return this.loadFromCoordsBool
    }

    fun nextLoadFromList(){
        this.loadFromCoordsBool = false
    }

    fun getDatabase(): MyDB? {
        return myDatabase
    }

    fun getTown(): String {
        return mtown
    }

    fun getUrl(): String? {
        return myUrl
    }

    fun changeTown(newTown: String) {
        this.mtown = newTown
        this.myUrl = "weather?q=$mtown&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
    }

    fun setUrl(url: String){
        this.myUrl = curLocUrl
    }

    fun setLat(lat:String){
        this.lat = lat
    }
    fun setLon(lon: String){
        this.lon = lon
    }

    fun getLon() : String{
        return lon
    }

    fun getLat() : String{
        return lat
    }
}