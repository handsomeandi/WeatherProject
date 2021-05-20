package com.example.weatherproj.weatherobjects

import com.example.weatherproj.MyApp
import com.example.weatherproj.MyDB
import com.example.weatherproj.TownDao
import com.example.weatherproj.Urls
import com.example.weatherproj.networkobjects.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//@Module
//class UrlModule {
//    @Singleton
//    @Provides
//    fun provideUrl(town : String): String {
//        return "weather?q=$town&appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
//    }
//
//    //TODO: provide towns from database
////    @Singleton
////    @Provides
////    fun provideTown(dao : TownDao, id: Int): String {
////        return dao.findById(id).name!!
////    }
//
//    @Singleton
//    @Provides
//    fun provideTown(): String {
//        return "Simferopol"
//    }
//
//    @Singleton
//    @Provides
//    fun provideDB(app: MyApp) : MyDB{
//        return app.getInstance()!!.getDatabase()!!
//    }
//
//    @Singleton
//    @Provides
//    fun provideDao(db : MyDB):TownDao{
//        return db.townDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideMyApp():MyApp{
//        return MyApp()
//    }
//
//
//
//}