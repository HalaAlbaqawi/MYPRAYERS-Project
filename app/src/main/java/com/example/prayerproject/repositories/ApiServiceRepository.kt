package com.example.prayerproject.repositories

import android.content.Context
import com.example.prayerproject.api.PrayersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://api.aladhan.com"
class ApiServiceRepository (val context: Context){

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //API FOR RETROFIT
    private val retrofitApi = retrofitService.create(PrayersApi::class.java)



    suspend fun getData(lat: Double, lon: Double) = retrofitApi.getData(lat, lon)


    companion object{
        private var instance: ApiServiceRepository? = null
        // design pattern
        fun init (conext: Context){

            if (instance == null)
                instance = ApiServiceRepository(conext)

        }
        fun get(): ApiServiceRepository {

            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }

    }



}