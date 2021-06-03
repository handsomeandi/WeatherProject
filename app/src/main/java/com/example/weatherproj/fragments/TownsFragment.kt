package com.example.weatherproj.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.R
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.TownsRecyclerViewAdapter
import com.example.weatherproj.presenters.TownsPresenter
import com.example.weatherproj.views.TownsView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class TownsFragment : MvpAppCompatFragment(), TownsView, View.OnClickListener {
    private lateinit var  changeCityBtn : Button
    private lateinit var  townEditText: EditText


    private lateinit var townsListView : RecyclerView
    private lateinit var adapter : TownsRecyclerViewAdapter

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
        adapter = TownsRecyclerViewAdapter()
        adapter.onClick = {
            onItemClick(it)
        }
        townsListView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        townsListView.adapter = adapter
        changeCityBtn.setOnClickListener(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance?.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_towns, container, false)
    }

    override fun addItem(town: TownClass) {
        adapter.addItem(town)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TownsFragment()
    }

    override fun updateList(towns: List<TownClass>) {
        adapter.setItems(towns)
    }

    override fun onClick(v: View?) {
        townsPresenter.onAddButtonClick(TownClass(townEditText.text.toString()))
    }

    private fun onItemClick(town: TownClass){
        townsPresenter.onTownClicked(town)
    }

    override fun switchToWeatherFrag() {
        activity?.let {
            LocalBroadcastManager.getInstance(it).sendBroadcast(Intent("change fragment").putExtra(
                Constants.FRAGMENT_CHANGE, Constants.BOTTOM_NAV_WEATHER_PAGE_ID))
        }
    }
}