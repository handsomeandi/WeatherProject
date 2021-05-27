package com.example.weatherproj.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherproj.R
import com.example.weatherproj.infoobjects.InfoPresenter
import com.example.weatherproj.views.InfoView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class InfoFragment : MvpAppCompatFragment(), InfoView {


    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter


    @ProvidePresenter
    fun provideInfoPresenter() : InfoPresenter {
        return InfoPresenter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InfoFragment().apply {
            }
    }
}