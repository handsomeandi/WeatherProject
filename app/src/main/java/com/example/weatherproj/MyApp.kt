package com.example.weatherproj

import android.app.Application
import androidx.room.Room




class MyApp : Application {
    constructor() : super()

    var minstance: MyApp? = null
    var myDatabase: MyDB? = null
    private var mtown : String? = "Simferopol"
    private var myUrl : String? = "weather?q=$mtown&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"


    override fun onCreate() {
        super.onCreate()
        minstance = this
        mtown = "Simferopol"
        myUrl = "weather?q=$mtown&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
        myDatabase =
            Room.databaseBuilder(this, MyDB::class.java, "WeatherDB").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    fun getInstance(): MyApp? {
        if(minstance == null){
            minstance = this
        }
        return minstance
    }

    fun getDatabase(): MyDB? {
        return myDatabase
    }

    fun getTown() : String?{
        return mtown
    }

    fun getUrl(): String?{
        return myUrl
    }

    fun changeTown(newTown : String){
        this.mtown = newTown
        this.myUrl = "weather?q=$mtown&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
    }
}