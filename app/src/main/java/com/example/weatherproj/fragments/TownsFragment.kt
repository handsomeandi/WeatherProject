package com.example.weatherproj.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherproj.MainApp
import com.example.weatherproj.R
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.Urls
import com.example.weatherproj.presenters.TownsPresenter
import com.example.weatherproj.views.TownsView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class TownsFragment : MvpAppCompatFragment(), TownsView, View.OnClickListener, AdapterView.OnItemClickListener {
    private lateinit var  changeCityBtn : Button
    private lateinit var  townEditText: EditText


    private lateinit var townsListView : ListView
    private lateinit var adapter : ArrayAdapter<String>

    @InjectPresenter
    lateinit var townsPresenter: TownsPresenter

    @Inject
    lateinit var presenterProvider: Provider<TownsPresenter>

    @ProvidePresenter
    fun providePresenter(): TownsPresenter {
        return presenterProvider.get()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        townsListView = view.findViewById(R.id.townsListView)
        changeCityBtn = view.findViewById(R.id.addTownBtn)
        townEditText = view.findViewById(R.id.townEditText)


        townsPresenter.onTownsRequired()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance!!.component!!.inject(this)
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
            TownsFragment()
    }

    override fun updateList(towns: List<TownClass>) {
        var townNames:ArrayList<String> = ArrayList()
        for(item in towns){
            townNames.add(item.name.toString())
        }
        adapter =  ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            townNames
        )
        townsListView.adapter = adapter
        townsListView.onItemClickListener = this
        changeCityBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        townsPresenter.onAddButtonClick(TownClass(townEditText.text.toString()))
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        townsPresenter.onTownClicked(TownClass(townsListView.getItemAtPosition(position).toString()))
    }

    override fun switchToWeatherFrag() {
        if(activity != null){
            LocalBroadcastManager.getInstance(activity!!).sendBroadcast(Intent("change fragment").putExtra(
            Urls.FRAGMENT_CHANGE, Urls.BOTTOM_NAV_WEATHER_PAGE_ID))
        }
    }
}