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
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.MainApp
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity


class MainActivity : MvpAppCompatActivity() {

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var fragTrans: FragmentTransaction

    private lateinit var fragManager: FragmentManager

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest

    private var PERMISSION_ID = 1000



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

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if(checkPermissions()){
            fusedLocationClient.lastLocation.addOnCompleteListener{task ->
                var location : Location? = task.result
                if(location != null){
                    Log.d("coords", String.format("Longitude: %s, Latitude: %s", location.longitude, location.latitude))
                    setCurrentLocation(location.latitude.toString(),location.longitude.toString())
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
            locationCallback,Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            var lastLocation : Location = p0!!.lastLocation
            Log.d("coords", String.format("Longitude: %s, Latitude: %s", lastLocation.longitude, lastLocation.latitude))
            setCurrentLocation(lastLocation.latitude.toString(),lastLocation.longitude.toString())
            changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)
        }
    }

    private fun setCurrentLocation(lat:String, lon:String){
        val myAppShared = MainApp.instance!!.getSharedPreferences(Constants.MY_PREFS,Context.MODE_PRIVATE)
        val editor = myAppShared.edit()
        editor.putString(Constants.LATITUDE,lat)
        editor.putString(Constants.LONGITUDE,lon)
        editor.apply()
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


    fun changeFrag(id : Int){
        var myFrag : Fragment? = null
        myFrag = when(id){
            R.id.weatherPage -> {
                WeatherFragment.newInstance()
            }
            R.id.townsPage -> {
                TownsFragment.newInstance()
            }
            R.id.infoPage -> {
                InfoFragment.newInstance()
            }
            else -> {
                WeatherFragment.newInstance()
            }

        }
        fragTrans = fragManager.beginTransaction()
        fragTrans.replace(R.id.frameLay, myFrag)
        fragTrans.commit()
        setBottomNavigationItem(id)
    }
}