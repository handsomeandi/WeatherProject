package com.example.weatherproj

import android.content.Context
import androidx.room.Room
import com.example.weatherproj.Urls.Companion.DB_NAME
import com.example.weatherproj.networkobjects.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [
    ContextModule::class
])
class DBModule {
    @Singleton
    @Provides
    fun provideDB(context: Context): MyDB {
        return Room.databaseBuilder(context, MyDB::class.java, DB_NAME).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(db: MyDB): TownDao {
        return db.townDao()
    }


}