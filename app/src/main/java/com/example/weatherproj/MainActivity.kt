package com.example.weatherproj

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherproj.databinding.ActivityMainBinding
import com.example.weatherproj.presenters.MainPresenter
import com.example.weatherproj.utils.Constants
import com.example.weatherproj.utils.Constants.Companion.BOTTOM_NAV_WEATHER_PAGE_ID
import com.example.weatherproj.utils.Constants.Companion.FRAGMENT_CHANGE
import com.example.weatherproj.utils.MainApp
import com.example.weatherproj.views.MainView
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.location.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator:Navigator = AppNavigator(this,R.id.frameLay)

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var binding: ActivityMainBinding

    private var permissionId = 1000

    @Inject
    lateinit var navigationHolder: NavigatorHolder



    @Inject
    lateinit var presenterProvider : Provider<MainPresenter>


    private val mainPresenter by moxyPresenter {
        presenterProvider.get()
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        registerReceiver()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            mainPresenter.changeFrag(item.itemId)
            true
        }
    }


    private fun registerReceiver(){
        val townReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                mainPresenter.changeFrag(intent.getIntExtra(FRAGMENT_CHANGE,R.id.weatherPage))
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(townReceiver, IntentFilter(Constants.INTENT_CHANGE_TO_WEATHER_FRAG))
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if(checkPermissions()){
            fusedLocationClient.lastLocation.addOnCompleteListener{task ->
                val location : Location? = task.result
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
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 2
        }
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
        override fun onLocationResult(result: LocationResult?) {
            val lastLocation : Location? = result?.lastLocation
            setCurrentLocation(lastLocation?.latitude.toString(),lastLocation?.longitude.toString())
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == permissionId){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }


    override fun setBottomNavigationItem(id : Int){
        binding.bottomNav.menu.findItem(id)?.isChecked = true
    }

    override fun onBackPressed() {
        mainPresenter.onBack()
    }

}