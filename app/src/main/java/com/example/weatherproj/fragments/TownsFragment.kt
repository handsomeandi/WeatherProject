package com.example.weatherproj.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.databaseobjects.TownClass
import com.example.weatherproj.databinding.FragmentTownsBinding
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.TownsRecyclerViewAdapter
import com.example.weatherproj.presenters.TownsPresenter
import com.example.weatherproj.views.TownsView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class TownsFragment : MvpAppCompatFragment(), TownsView, View.OnClickListener {

    private lateinit var binding: FragmentTownsBinding

    private lateinit var adapter : TownsRecyclerViewAdapter

    @Inject
    lateinit var presenterProvider: Provider<TownsPresenter>

    private val townsPresenter by moxyPresenter {
        presenterProvider.get()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TownsRecyclerViewAdapter()
        adapter.onClick = {
            onItemClick(it)
        }
        binding.townsListView.let {
            it.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }
        binding.addTownBtn.setOnClickListener(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance?.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTownsBinding.inflate(inflater, container, false)
        return binding.root
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
        townsPresenter.onAddButtonClick(TownClass(binding.townEditText.text.toString()))
    }

    private fun onItemClick(town: TownClass){
        townsPresenter.onTownClicked(town)
    }

    override fun switchToWeatherFrag() {
        activity?.let {
            LocalBroadcastManager.getInstance(it).sendBroadcast(Intent(Constants.INTENT_CHANGE_TO_WEATHER_FRAG).putExtra(
                Constants.FRAGMENT_CHANGE, Constants.BOTTOM_NAV_WEATHER_PAGE_ID))
        }
    }
}