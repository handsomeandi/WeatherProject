package com.example.weatherproj

import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DBModule::class, ContextModule::class, DataModule::class, NavModule::class])
interface MainComponent {
    fun inject(townsFragment: TownsFragment)
    fun inject(weatherFragment: WeatherFragment)
    fun inject(target: MainActivity)
}