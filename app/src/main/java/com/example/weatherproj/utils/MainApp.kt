package com.example.weatherproj.utils

import android.app.Application
import com.example.weatherproj.DaggerMainComponent
import com.example.weatherproj.MainComponent
import com.example.weatherproj.modules.*


class MainApp() : Application() {
    var component: MainComponent? = null

    companion object {
        var instance: MainApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerMainComponent.builder().contextModule(ContextModule(this)).dBModule(
            DBModule()
        ).networkModule(NetworkModule()).dataModule(DataModule()).navModule(NavModule()).build()
    }

}