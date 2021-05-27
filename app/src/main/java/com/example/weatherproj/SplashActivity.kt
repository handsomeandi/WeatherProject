package com.example.weatherproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.liveData
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private var DELAYTIME : Long = 3000;
    val splashScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScope.launch {
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            delay(DELAYTIME)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        splashScope.cancel()
        super.onPause()
    }
}