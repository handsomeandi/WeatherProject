package com.example.weatherproj.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(val context: Context) {
    @Provides
    fun context(): Context {
        return context
    }
}