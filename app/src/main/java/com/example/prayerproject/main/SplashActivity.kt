package com.example.prayerproject.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.prayerproject.R
import com.example.prayerproject.repositories.ApiServiceAthkarRepository
import com.example.prayerproject.repositories.ApiServiceRepository

lateinit var handler: Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //initializing the companion object on repositories
        ApiServiceRepository.init(this)
        ApiServiceAthkarRepository.init(this)

        window.navigationBarColor =
            this.resources.getColor(R.color.black) // this is for the navigation bar color of the android system
        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } , 2000)


    }

}