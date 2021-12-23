package com.example.prayerproject.repositories

import android.content.Context
import com.example.prayerproject.api.QiblaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceQiblaRepository(val context: Context) {

    val retrofit = Retrofit.Builder()
        .baseUrl(" https://api.aladhan.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val qiblaApi = retrofit.create(QiblaApi::class.java)

    suspend fun getQiblaDirection(lat: Double, lon: Double) = qiblaApi.getQiblaDirection(lat,lon)

    companion object {
        private var instance: ApiServiceQiblaRepository? = null

        // design pattern
        fun init(context: Context) {

            if (instance == null)
                instance = ApiServiceQiblaRepository(context)

        }

        fun get(): ApiServiceQiblaRepository {

            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }

    }

}