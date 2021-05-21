package com.example.weatherproj.networkobjects

import android.content.Context
import com.example.weatherproj.ContextModule
import com.example.weatherproj.DBModule
import com.example.weatherproj.MyDB
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.townsobjects.TownsPresenter
import com.example.weatherproj.weatherobjects.WeatherPresenter
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DBModule::class, ContextModule::class])
interface MainComponent {
    fun getServerApi(): ServerApi
    fun getDatabase() : MyDB
    fun getContext() : Context
    fun inject(townsFragment: TownsFragment)
    fun inject(weatherFragment: WeatherFragment)
    //fun inject(townsPresenter: TownsPresenter)
   // fun inject(weatherPresenter: WeatherPresenter)
}