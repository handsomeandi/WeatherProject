package com.example.weatherproj

import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherproj.Urls.Companion.BOTTOM_NAV_WEATHER_PAGE_ID
import com.example.weatherproj.Urls.Companion.FRAGMENT_CHANGE
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.presenters.MainPresenter
import com.example.weatherproj.views.MainView
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var fragTrans: FragmentTransaction

    private lateinit var fragManager: FragmentManager

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest

    var PERMISSION_ID = 1000


    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() : MainPresenter {
        return MainPresenter()
    }

    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun accessPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        

        fragManager = supportFragmentManager
        fragTrans = fragManager.beginTransaction()
        lateinit var myFrag : Fragment
        bottomNavigationView = findViewById(com.example.weatherproj.R.id.bottomNav)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        getLastLocation()


        registerReceiver()



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            changeFrag(item.itemId)
            true

        }
    }



    private fun registerReceiver(){
        var act2InitReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                changeFrag(intent.getIntExtra(FRAGMENT_CHANGE,-10))
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(act2InitReceiver, IntentFilter("change fragment"))
    }

    private fun getLastLocation(){
        if(checkPermissions()){
            fusedLocationClient.lastLocation.addOnCompleteListener{task ->
                var location : Location? = task.result
                if(location != null){
                    Log.d("coords", String.format("Longitude: %s, Latitude: %s", location.longitude, location.latitude))
                    setCurLoc(location.latitude.toString(),location.longitude.toString())
                    changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)
                }else{
                    getNewLocation()
                }
            }
        }else{
            accessPermissions()
        }
    }

    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient!!.requestLocationUpdates(locationRequest,
            locationCallback,Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            var lastLocation : Location = p0!!.lastLocation
            Log.d("coords", String.format("Longitude: %s, Latitude: %s", lastLocation.longitude, lastLocation.latitude))
            setCurLoc(lastLocation.latitude.toString(),lastLocation.longitude.toString())
            changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)
        }
    }

    private fun setCurLoc(lat:String,lon:String){
        MyApp.minstance!!.setLat(lat)
        MyApp.minstance!!.setLon(lon)
        MyApp.minstance!!.setUrl(Urls.URL_BY_COORD)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("check", "You have a permission")
                getLastLocation()
            }
        }
    }


    private fun setBottomNavigationItem(id: Int){
        bottomNavigationView.getMenu().findItem(id).setChecked(true);
    }


    override fun onStop() {
        super.onStop()

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)
        mainPresenter.removeWeatherFromShared(sharedPreferences)

    }

    override fun changeFrag(id : Int){
        var myFrag : Fragment? = null
        when(id){
            R.id.weatherPage -> {
                myFrag = WeatherFragment.newInstance()
            }
            R.id.townsPage -> {
                myFrag = TownsFragment.newInstance()
            }
            R.id.infoPage -> {
                myFrag = InfoFragment.newInstance()
            }
            else -> {
                myFrag = WeatherFragment.newInstance()
            }

        }
        fragTrans = fragManager.beginTransaction()
        fragTrans.replace(R.id.frameLay, myFrag)
        fragTrans.commit()
        setBottomNavigationItem(id)
    }


}