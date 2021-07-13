package com.example.weatherproj.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.MainApp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {
    @Singleton
    @Provides
    fun provideGson() : Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideShared() : SharedPreferences{
        return MainApp.instance.getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE)
    }

}