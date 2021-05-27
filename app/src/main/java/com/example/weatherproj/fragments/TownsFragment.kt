package com.example.weatherproj.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherproj.MyApp
import com.example.weatherproj.R
import com.example.weatherproj.TownClass
import com.example.weatherproj.Urls
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
class TownsFragment : MvpAppCompatFragment(), TownsView, View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  changeCityBtn : Button
    private lateinit var  townEditText: EditText
    private lateinit var townName : String


    private lateinit var townsListView : ListView
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var listOfTowns : List<String>

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
        townsListView = view.findViewById(R.id.townsListView)
        changeCityBtn = view.findViewById(R.id.addTownBtn)
        townEditText = view.findViewById(R.id.townEditText)


        listOfTowns = townsPresenter.getAllTownNames()

        adapter = object: ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_list_item_1,
            listOfTowns
        ){}
        townsListView.adapter = adapter
        townsListView.onItemClickListener = this
        changeCityBtn.setOnClickListener(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.minstance!!.component!!.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_towns, container, false)
    }

    companion object {
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
                    var townObj : TownClass = TownClass(townName)
                    townsPresenter.changeTown(townName)
                    townsPresenter.addTownToDB(townObj)
                    var savedTowns : List<TownClass> = townsPresenter.getAllTowns()
                    for (town in savedTowns){
                        Log.d("trackDB", "name: " + town.name.toString() + " id:" + town.id)
                    }
                    listOfTowns = townsPresenter.getAllTownNames()
                    adapter.clear()
                    adapter.addAll(listOfTowns)
//                    townsPresenter.addTownToDB(townObj)
                }
            }
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        townsPresenter.changeTown(townsListView.getItemAtPosition(position).toString())
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(Intent("change fragment").putExtra(
            Urls.FRAGMENT_CHANGE, Urls.BOTTOM_NAV_WEATHER_PAGE_ID))
//        var fragmentTransaction : FragmentTransaction = getActivity()!!.getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frameLay, WeatherFragment());
//        fragmentTransaction.commit()
    }
}