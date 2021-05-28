package com.example.weatherproj

import android.content.Context
import com.example.weatherproj.databaseobjects.MyDB
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.modules.ContextModule
import com.example.weatherproj.modules.DBModule
import com.example.weatherproj.modules.NetworkModule
import com.example.weatherproj.networkobjects.ServerApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DBModule::class, ContextModule::class])
interface MainComponent {
    fun inject(townsFragment: TownsFragment)
    fun inject(weatherFragment: WeatherFragment)
}