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
import com.example.weatherproj.Urls.Companion.BOTTOM_NAV_INFO_PAGE_ID
import com.example.weatherproj.Urls.Companion.BOTTOM_NAV_TOWNS_PAGE_ID
import com.example.weatherproj.Urls.Companion.BOTTOM_NAV_WEATHER_PAGE_ID
import com.example.weatherproj.Urls.Companion.FRAGMENT_CHANGE
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
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
//    companion object {
//        private const val LOCATION_FINE_CODE = 100
//        private const val LOCATION_COARSE_CODE = 101
//        private const val PERMISSION_ID = 42
//
//    }

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

        changeFrag(BOTTOM_NAV_WEATHER_PAGE_ID)

        registerReceiver()



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            changeFrag(item.itemId)
            true

        }





//        var grantPermissions : Boolean = checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION)
//
//        if (grantPermissions) {
//            fusedLocationClient?.lastLocation?.
//            addOnSuccessListener(this,
//                {location : Location? ->
//                    // Got last known location. In some rare
//                    // situations this can be null.
//                    if(location == null) {
//                        // TODO, handle it
//                    } else location.apply {
//                        // Handle location object
//                        Log.d("location", location.toString())
//                    }
//                })
//        }

//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//
//            fusedLocationClient?.lastLocation?.addOnSuccessListener(this,
//                {location : Location? ->
//                    // Got last known location. In some rare
//                    // situations this can be null.
//                    if(location == null) {
//                        // TODO, handle it
//                    } else location.apply {
//                        // Handle location object
//                        Log.d("location", location.toString())
//                    val gcd = Geocoder(this@MainActivity, Locale.getDefault())
//                    val addresses: List<Address> =
//                        gcd.getFromLocation(location.latitude, location.longitude, 1)
//                    if (addresses.size > 0) {
//                        Log.d("Location", addresses[0].getLocality())
//                    } else {
//                        // do your stuff
//                    }
//                    }
//                })
//        }





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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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

        }
    }

    private fun setCurLoc(lat:String,lon:String){
        MyApp.minstance!!.setLat(lat)
        MyApp.minstance!!.setLon(lon)
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

//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            PERMISSION_ID -> {
//            }
//        }
//    }
//
//    private fun checkPermission(vararg perm:String) : Boolean {
//        val havePermissions = perm.toList().all {
//            ContextCompat.checkSelfPermission(this,it) ==
//                    PackageManager.PERMISSION_GRANTED
//        }
//        if (!havePermissions) {
//            if(perm.toList().any {
//                    ActivityCompat.
//                    shouldShowRequestPermissionRationale(this, it)}
//            ) {
//                val dialog = AlertDialog.Builder(this)
//                    .setTitle("Permission")
//                    .setMessage("Permission needed!")
//                    .setPositiveButton("OK", {id, v ->
//                        ActivityCompat.requestPermissions(
//                            this, perm, PERMISSION_ID)
//                    })
//                    .setNegativeButton("No", {id, v -> })
//                    .create()
//                dialog.show()
//            } else {
//                ActivityCompat.requestPermissions(this, perm, PERMISSION_ID)
//            }
//            return false
//        }
//        return true
//    }


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