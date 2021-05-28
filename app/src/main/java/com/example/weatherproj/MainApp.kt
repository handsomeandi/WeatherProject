package com.example.weatherproj

import android.app.Application
import com.example.weatherproj.modules.ContextModule
import com.example.weatherproj.modules.DBModule
import com.example.weatherproj.modules.NetworkModule


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
        ).networkModule(NetworkModule()).build()
    }

}