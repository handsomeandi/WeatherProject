package com.example.weatherproj.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.weatherproj.MainPresenter
import com.example.weatherproj.MyApp
import com.example.weatherproj.R
import com.example.weatherproj.TownClass
import com.example.weatherproj.townsobjects.TownsPresenter
import com.example.weatherproj.townsobjects.TownsView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TownsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TownsFragment : MvpAppCompatFragment(), TownsView, View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  changeCityBtn : Button
    private lateinit var  townEditText: EditText
    private lateinit var townName : String


    private lateinit var townsSpinner : Spinner

    @InjectPresenter
    lateinit var townsPresenter: TownsPresenter

    @Inject
    lateinit var presenterProvider: Provider<TownsPresenter>

    @ProvidePresenter
    fun providePresenter(): TownsPresenter {
        return presenterProvider.get()
    }



    /*@ProvidePresenter
    fun provideTownsPresenter() : TownsPresenter {
        return TownsPresenter()
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        townsSpinner = view.findViewById(R.id.townsSpinner)
        changeCityBtn = view.findViewById(R.id.addTownBtn)
        townEditText = view.findViewById(R.id.townEditText)


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

        changeCityBtn.setOnClickListener(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.minstance!!.component!!.inject(this)



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

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.addTownBtn -> if(!townEditText.text.isEmpty()){
                    townName = townEditText.text.toString()
                    var townObj : TownClass = TownClass(1,townName)
                    townsPresenter.changeTown(townName)
                    townsPresenter.addTownToDB(townObj)
                }
            }
        }
    }
}