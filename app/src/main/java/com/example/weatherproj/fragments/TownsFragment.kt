package com.example.weatherproj.fragments

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.weatherproj.R
import com.example.weatherproj.townsobjects.TownsPresenter
import com.example.weatherproj.townsobjects.TownsView
import com.example.weatherproj.weatherobjects.WeatherPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TownsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TownsFragment : MvpAppCompatFragment(), TownsView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var townsSpinner : Spinner

    @InjectPresenter
    lateinit var townsPresenter: TownsPresenter


    @ProvidePresenter
    fun provideTownsPresenter() : TownsPresenter {
        return TownsPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        townsSpinner = view.findViewById(R.id.townsSpinner)

        val list = mutableListOf(
            "Jungle green",
            "Light periwinkle",
            "Lemon glacier",
            "Kelly green",
            "Lemon yellow",
            "Lavender blush"
        )

        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){}
        // finally, data bind spinner with adapter
        townsSpinner.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_towns, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TownsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TownsFragment().apply {
            }
    }

    override fun updateList(towns: List<String>) {
        TODO("Not yet implemented")
    }

    override fun addToList(town: String) {
        TODO("Not yet implemented")
    }
}