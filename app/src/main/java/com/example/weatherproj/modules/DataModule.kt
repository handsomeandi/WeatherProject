package com.example.weatherproj.modules

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
}