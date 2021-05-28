package com.example.weatherproj.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherproj.databaseobjects.MyDB
import com.example.weatherproj.databaseobjects.TownDao
import com.example.weatherproj.Urls.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [
    ContextModule::class
])
class DBModule {
    @Singleton
    @Provides
    fun provideDB(context: Context): MyDB {
        return Room.databaseBuilder(context, MyDB::class.java, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(db: MyDB): TownDao {
        return db.townDao()
    }


}