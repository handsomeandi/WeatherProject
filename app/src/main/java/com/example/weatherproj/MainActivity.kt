package com.example.weatherproj

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var fragTrans: FragmentTransaction

    private lateinit var fragManager: FragmentManager

    var fusedLocationClient: FusedLocationProviderClient? = null

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        

        fragManager = supportFragmentManager
        fragTrans = fragManager.beginTransaction()
        lateinit var myFrag : Fragment
        bottomNavigationView = findViewById(com.example.weatherproj.R.id.bottomNav)


        myFrag = WeatherFragment.newInstance()
        changeFrag(myFrag)



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.weatherPage -> {
                    myFrag = WeatherFragment.newInstance()
                    changeFrag(myFrag)
                    true
                }
                R.id.townsPage -> {
                    myFrag = TownsFragment.newInstance()
                    changeFrag(myFrag)
                    true
                }
                R.id.infoPage -> {
                    myFrag = InfoFragment.newInstance()
                    changeFrag(myFrag)
                    true
                }
                else -> false

            }

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





    override fun onStop() {
        super.onStop()

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)
        mainPresenter.removeWeatherFromShared(sharedPreferences)

    }

    override fun changeFrag(fragment: Fragment){
        fragTrans = fragManager.beginTransaction()
        fragTrans.replace(R.id.frameLay, fragment)
        fragTrans.commit()
    }


}