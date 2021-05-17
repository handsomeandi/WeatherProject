package com.example.weatherproj.networkobjects

import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface NetworkComponent {
    fun getRetrofit(): Retrofit
    fun getClient(): OkHttpClient.Builder
    fun getServerApi(): ServerApi
}