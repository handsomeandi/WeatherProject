package com.example.weatherproj.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class Screens(private val fragment: Fragment): FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment {
        return fragment
    }

    companion object {
        fun weatherScreen() = Screens(WeatherFragment.newInstance())
        fun townsScreen() = Screens(TownsFragment.newInstance())
        fun infoScreen() = Screens(InfoFragment.newInstance())
    }
}