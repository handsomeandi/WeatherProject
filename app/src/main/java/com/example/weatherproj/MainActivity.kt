package com.example.weatherproj

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.weatherproj.fragments.InfoFragment
import com.example.weatherproj.fragments.TownsFragment
import com.example.weatherproj.fragments.WeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var fragTrans: FragmentTransaction

    private lateinit var fragManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        fragManager = supportFragmentManager

        fragTrans = fragManager.beginTransaction()
        lateinit var myFrag : Fragment
        bottomNavigationView = findViewById(com.example.weatherproj.R.id.bottomNav)


        myFrag = WeatherFragment.newInstance()
        fragTrans.add(R.id.frameLay, myFrag)
        fragTrans.commit()

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

    }



    override fun onStop() {
        super.onStop()

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(Urls.MY_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(Urls.MY_WEATHER)
        editor.commit()
        var testString : String? = sharedPreferences.getString(Urls.MY_WEATHER, "");
        Log.d("trackshared", "shared destroyed, test string: " + testString)

    }

    private fun changeFrag(fragment: Fragment){
        fragTrans = fragManager.beginTransaction()
        fragTrans.replace(R.id.frameLay, fragment)
        fragTrans.commit()
    }
}