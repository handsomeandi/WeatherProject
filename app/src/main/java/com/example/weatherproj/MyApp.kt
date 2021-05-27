package com.example.weatherproj

import android.app.Application
import com.example.weatherproj.databaseobjects.MyDB
import com.example.weatherproj.modules.ContextModule
import com.example.weatherproj.modules.DBModule
import com.example.weatherproj.networkobjects.DaggerMainComponent
import com.example.weatherproj.modules.NetworkModule


class MyApp() : Application() {

    private var mtown: String = "Simferopol"
    private var myUrl: String = ""
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
        component = DaggerMainComponent.builder().contextModule(ContextModule(this)).dBModule(
            DBModule()
        ).networkModule(NetworkModule()).build()
       }

    fun loadFromCoords() : Boolean{
        return this.loadFromCoordsBool
    }

    fun nextLoadFromList(){
        this.loadFromCoordsBool = false
    }

    fun getTown(): String {
        return mtown
    }

    fun getUrl(): String? {
        return myUrl
    }

    fun changeTown(newTown: String) {
        this.mtown = newTown
    }

    fun setUrl(url: String){
        this.myUrl = url
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