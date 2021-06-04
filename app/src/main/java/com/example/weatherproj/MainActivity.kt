package com.example.weatherproj

import android.annotation.SuppressLint
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
import com.example.weatherproj.utils.Constants.Companion.BOTTOM_NAV_WEATHER_PAGE_ID
import com.example.weatherproj.utils.Constants.Companion.FRAGMENT_CHANGE
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.example.weatherproj.presenters.MainPresenter
import com.example.weatherproj.presenters.WeatherPresenter
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.views.MainView
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

import javax.inject.Inject
import javax.inject.Provider


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var bottomNavigationView : BottomNavigationView

    private lateinit var navigator:Navigator

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest

    private var permissionId = 1000

    @Inject
    lateinit var navigationHolder: NavigatorHolder


    @InjectPresenter
    lateinit var mainPresenter: MainPresenter


    @Inject
    lateinit var presenterProvider : Provider<MainPresenter>


    @ProvidePresenter
    fun provideMainPresenter() : MainPresenter {
        return presenterProvider.get()
    }



    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun accessPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), permissionId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        MainApp.instance?.component?.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = AppNavigator(this,R.id.frameLay,supportFragmentManager)
        navigationHolder.setNavigator(navigator)
        bottomNavigationView = findViewById(R.id.bottomNav)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        registerReceiver()
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            mainPresenter.changeFrag(item.itemId)
            true
        }
    }


    private fun registerReceiver(){
        var act2InitReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                mainPresenter.changeFrag(intent.getIntExtra(FRAGMENT_CHANGE,-10))
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(act2InitReceiver, IntentFilter(Constants.INTENT_CHANGE_TO_WEATHER_FRAG))
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if(checkPermissions()){
            fusedLocationClient.lastLocation.addOnCompleteListener{task ->
                var location : Location? = task.result
                if(location != null){
                    setCurrentLocation(location.latitude.toString(),location.longitude.toString())
                    mainPresenter.changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)
                }else{
                    getNewLocation()
                }
            }
        }else{
            accessPermissions()
        }
    }

    private fun getNewLocation(){
        locationRequest = LocationRequest.create()
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
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,Looper.myLooper()!!
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            var lastLocation : Location = p0!!.lastLocation
            setCurrentLocation(lastLocation.latitude.toString(),lastLocation.longitude.toString())
            mainPresenter.changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)
        }
    }

    private fun setCurrentLocation(lat:String, lon:String){
        val myAppShared = MainApp.instance?.getSharedPreferences(Constants.MY_PREFS,Context.MODE_PRIVATE)
        val editor = myAppShared?.edit()
        editor?.putString(Constants.LATITUDE,lat)
        editor?.putString(Constants.LONGITUDE,lon)
        editor?.apply()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == permissionId){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }


    override fun setBottomNavigationItem(id: Int?){
        if(id!=null){
            bottomNavigationView.menu.findItem(id).isChecked = true;
        }
    }

}