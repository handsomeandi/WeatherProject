package com.example.weatherproj.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.lifecycle.Observer
import com.example.weatherproj.*
import com.example.weatherproj.weatherobjects.WeatherPresenter
import com.example.weatherproj.weatherobjects.WeatherView
import com.example.weatherproj.weatherobjects.Weather
import com.google.gson.Gson
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
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : MvpAppCompatFragment(R.layout.fragment_weather),
    WeatherView {


    // TODO: Rename and change types of parameters
    private lateinit var sunset : TextView
    private lateinit var wetness : TextView
    private lateinit var wind_speed : TextView
    private lateinit var weather_cond : TextView
    private lateinit var sunrise : TextView
    private lateinit var townName : TextView
    private lateinit var current_temp : TextView
    private lateinit var swipeWeather : SwipeRefreshLayout
    val ERROR = "ERROR"
    var gson : Gson = Gson()
    lateinit var sharedPreferences : SharedPreferences


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
        wind_speed = view.findViewById(R.id.githubLabel)
        weather_cond = view.findViewById(R.id.phoneNumLabel)
        wetness = view.findViewById(R.id.librariesLabel)
        current_temp = view.findViewById(R.id.currentTempTv)
        swipeWeather = view.findViewById(R.id.swipeWeather)
        townName = view.findViewById(R.id.townName)

        sharedPreferences =  activity!!.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)
        var weatherFromShared : String? = sharedPreferences.getString(
            Urls.MY_WEATHER,"")

        if(MyApp.minstance!!.loadFromCoords()){
            setupObserver(weatherPresenter.loadCurlocWeatherFromApi())
            MyApp.minstance!!.nextLoadFromList()
        }else{

            if(weatherFromShared!!.length > 0 && weatherFromShared != null && weatherFromShared != "null"){
                var weatherParsed : Weather = gson.fromJson(weatherFromShared, Weather::class.java)
                var currentTownName : String = MyApp.minstance!!.getTown()
                if(currentTownName == weatherParsed.getTownName()){
                    weatherPresenter.loadFromShared(weatherFromShared)
                }else{
                    setupObserver(weatherPresenter.loadDataFromApi())
                }
            }else{
                setupObserver(weatherPresenter.loadDataFromApi())
            }
        }
      //  sharedPreferences =  activity!!.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)

        if(swipeWeather != null)    {
            swipeWeather.setOnRefreshListener{
                Handler().postDelayed(Runnable {
                    weatherPresenter.removeWeather(sharedPreferences)
                    setupObserver(weatherPresenter.loadDataFromApi())
                    swipeWeather.isRefreshing = false
                }, 1000)
            }
        }




    }



    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.minstance!!.component!!.inject(this)

        super.onCreate(savedInstanceState)


        //


    }

    //TODO: Сделать добавление текущего города в базу данных

    private fun setupObserver(liveData : LiveData<Resource<Weather>>) {
//        if()
//        weatherPresenter.loadDataFromApi()
        liveData.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { weather -> showWeather(weather)
                            MyApp.minstance!!.changeTown(weather.getTownName()!!)
                            Log.d("track", "loaded from api")
                            var savedWeatherData : String = gson.toJson(weather!!)
                            saveWeather(savedWeatherData, sharedPreferences)}
                    }
                    Status.ERROR -> {
                        Toast.makeText( activity,it.message!!,Toast.LENGTH_SHORT)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }


    private fun saveWeather(weather : String, sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.putString(Urls.MY_WEATHER, weather)
        editor.commit()

        Log.d("SharedPrefs", sharedPreferences.getString(Urls.MY_WEATHER, "")!!)
    }



    override fun showWeather(weather: Weather) {
        sunset.setText(
            java.lang.String.format(
                "%s%s",
                getString(R.string.sunset),
                weather.getSunset()
            )
        )
        wetness.setText(
            java.lang.String.format(
                "%s%s",
                getString(R.string.humidity),
                weather.getHumidity()
            )
        )
        wind_speed.setText(
            java.lang.String.format(
                "%s%s",
                getString(R.string.wind_speed),
                weather.getWindSpeed()
            )
        )
        weather_cond.setText(
            java.lang.String.format(
                "%s%s",
                getString(R.string.weather_cond),
                weather.getWeatherConditions()
            )
        )
        sunrise.setText(
            java.lang.String.format(
                "%s%s",
                getString(R.string.sunrise),
                weather.getSunrise()
            )
        )
        current_temp.setText(
            java.lang.String.format(
                "%s%s C",
                getString(R.string.current_temp),
                weather.getTemp()
            )
        )
        townName.setText(
            java.lang.String.format("%s%s",getString(R.string.town_name), weather.getTownName())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            WeatherFragment().apply {
            }
    }
}