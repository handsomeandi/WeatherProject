package com.example.weatherproj.modules

import com.example.weatherproj.Urls
import com.example.weatherproj.networkobjects.ServerApi
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
        client.connectTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        client.readTimeout(60, TimeUnit.SECONDS)
        return Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client.build())
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
                CoroutineCallAdapterFactory()
            ).build()
    }

    @Singleton
    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }

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



}