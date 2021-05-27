package com.example.weatherproj.presenters

import android.content.SharedPreferences
import android.util.Log
import com.example.weatherproj.views.MainView
import com.example.weatherproj.Urls
import moxy.InjectViewState
import moxy.MvpPresenter


@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {


    fun removeWeatherFromShared(sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.commit()
        var testString : String? = sharedPreferences.getString(Urls.MY_WEATHER, "");
        Log.d("trackshared", "shared destroyed, test string: " + testString)
    }

}