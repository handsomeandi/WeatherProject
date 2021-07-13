package com.example.weatherproj.modules

import com.example.weatherproj.BuildConfig
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.networkobjects.ServerApi
import com.example.weatherproj.utils.NetworkHandler
import com.example.weatherproj.utils.RetrofitNetworkHandler
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient.Builder): Retrofit {
        client.let {
            it.connectTimeout(30,TimeUnit.SECONDS)
            it.writeTimeout(60, TimeUnit.SECONDS)
            it.readTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client.build())
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
                CoroutineCallAdapterFactory()
            ).build()
    }

    @Singleton
    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor)
    }

    @Singleton
    @Provides
    fun provideLogger() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(serverApi: ServerApi) : NetworkHandler = RetrofitNetworkHandler(serverApi)



}