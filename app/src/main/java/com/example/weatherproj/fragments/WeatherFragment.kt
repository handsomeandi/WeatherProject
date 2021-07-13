package com.example.weatherproj.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherproj.*
import com.example.weatherproj.databinding.FragmentWeatherBinding
import com.example.weatherproj.presenters.WeatherPresenter
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.views.WeatherView
import com.example.weatherproj.weatherobjects.Weather
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class WeatherFragment : MvpAppCompatFragment(R.layout.fragment_weather),
    WeatherView {

    private lateinit var binding: FragmentWeatherBinding


    @Inject
    lateinit var presenterProvider : Provider<WeatherPresenter>

    private val weatherPresenter by moxyPresenter{
        presenterProvider.get()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherPresenter.onWeatherRequired()
        binding.swipeWeather.setOnRefreshListener{
            weatherPresenter.onWeatherRequired(true)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance?.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun showWeather(weather: Weather?) {
        if(weather!=null){
            binding.let {
                it.swipeWeather.isRefreshing = false
                it.sunsetTv.text = "${getString(R.string.sunset_string)}${weather.getSunset()}"
                it.humidityLabel.text ="${getString(R.string.wetness_string)}${weather.getHumidity()}"
                it.windSpeedLabel.text = "${getString(R.string.wind_speed_string)}${weather.getWindSpeed()}"
                it.weatherCondTv.text = "${getString(R.string.weather_cond_string)}${weather.getWeatherConditions()}"
                it.sunriseTv.text = "${getString(R.string.sunrise_string)}${weather.getSunrise()}"
                it.currentTempTv.text = "${getString(R.string.current_temp_string)}${weather.getTemp()}"
                it.townName.text = "${getString(R.string.town_string)}${weather.townName}"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            WeatherFragment()
    }

}