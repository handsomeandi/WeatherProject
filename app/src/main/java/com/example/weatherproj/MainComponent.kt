package com.example.weatherproj

import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.modules.ContextModule
import com.example.weatherproj.modules.DBModule
import com.example.weatherproj.modules.DataModule
import com.example.weatherproj.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DBModule::class, ContextModule::class, DataModule::class])
interface MainComponent {
    fun inject(townsFragment: TownsFragment)
    fun inject(weatherFragment: WeatherFragment)
}