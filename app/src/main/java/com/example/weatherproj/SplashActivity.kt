package com.example.weatherproj

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private var delayTime : Long = 3000;
    private val splashScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScope.launch {
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            delay(delayTime)
            startActivity(intent)
            finish()
        }
    }
}