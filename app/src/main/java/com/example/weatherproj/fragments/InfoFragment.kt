package com.example.weatherproj.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherproj.R
import moxy.MvpAppCompatFragment

class InfoFragment : MvpAppCompatFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InfoFragment()
    }
}