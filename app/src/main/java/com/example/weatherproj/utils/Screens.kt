package com.example.weatherproj.utils

import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen


object Screens{
    fun weatherScreen() = FragmentScreen{
        WeatherFragment.newInstance()
    }
    fun townsScreen() = FragmentScreen{
        TownsFragment.newInstance()
    }
    fun infoScreen() = FragmentScreen{
        InfoFragment.newInstance()
    }
}