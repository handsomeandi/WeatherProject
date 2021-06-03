package com.example.weatherproj.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherproj.*
import com.example.weatherproj.presenters.WeatherPresenter
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.views.WeatherView
import com.example.weatherproj.weatherobjects.Weather
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class WeatherFragment : MvpAppCompatFragment(R.layout.fragment_weather),
    WeatherView {
    private lateinit var sunset : TextView
    private lateinit var wetness : TextView
    private lateinit var windSpeed : TextView
    private lateinit var weatherCond : TextView
    private lateinit var sunrise : TextView
    private lateinit var townName : TextView
    private lateinit var currentTemp : TextView
    private lateinit var swipeWeather : SwipeRefreshLayout


    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter


    @Inject
    lateinit var presenterProvider : Provider<WeatherPresenter>


    @ProvidePresenter
    fun provideWeatherPresenter() : WeatherPresenter {
        return presenterProvider.get()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sunset = view.findViewById(R.id.sunsetTv)
        sunrise = view.findViewById(R.id.sunriseTv)
        windSpeed = view.findViewById(R.id.githubLabel)
        weatherCond = view.findViewById(R.id.phoneNumLabel)
        wetness = view.findViewById(R.id.librariesLabel)
        currentTemp = view.findViewById(R.id.currentTempTv)
        swipeWeather = view.findViewById(R.id.swipeWeather)
        townName = view.findViewById(R.id.townName)

        weatherPresenter.onWeatherRequired()
        swipeWeather?.setOnRefreshListener{
            weatherPresenter.onWeatherRequired(true)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance!!.component!!.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun showWeather(weather: Weather?) {
        if(weather!=null){
            swipeWeather.isRefreshing = false
            sunset.text = java.lang.String.format(
                "%s%s",
                getString(R.string.sunset_string),
                weather.getSunset()
            )
            wetness.text = java.lang.String.format(
                "%s%s",
                getString(R.string.wetness_string),
                weather.getHumidity()
            )
            windSpeed.text = java.lang.String.format(
                "%s%s",
                getString(R.string.wind_speed_string),
                weather.getWindSpeed()
            )
            weatherCond.text = java.lang.String.format(
                "%s%s",
                getString(R.string.weather_cond_string),
                weather.getWeatherConditions()
            )
            sunrise.text = java.lang.String.format(
                "%s%s",
                getString(R.string.sunrise_string),
                weather.getSunrise()
            )
            currentTemp.text = java.lang.String.format(
                "%s%s C",
                getString(R.string.current_temp_string),
                weather.getTemp()
            )
            townName.text = java.lang.String.format("%s%s",getString(R.string.town_string), weather.townName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            WeatherFragment()
    }

}