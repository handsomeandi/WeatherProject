package com.example.weatherproj.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavModule {
    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router>{
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>):Router{
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigationHolder(cicerone: Cicerone<Router>) : NavigatorHolder{
        return cicerone.getNavigatorHolder()
    }
}