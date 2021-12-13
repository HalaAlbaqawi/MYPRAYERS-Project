package com.example.prayerproject.repositories

import android.content.Context
import com.example.prayerproject.api.AthkarApi
import com.example.prayerproject.api.PrayersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://61ae0fdca7c7f3001786f5c4.mockapi.io"

class ApiServiceAthkarRepository(val context: Context) {



    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //API FOR RETROFIT
    private val retrofitApi = retrofitService.create(AthkarApi::class.java)



   suspend fun getAthkar() = retrofitApi.getAthkar()


    companion object{
        private var instance: ApiServiceAthkarRepository? = null
        // design pattern
        fun init (conext: Context){

            if (instance == null)
                instance = ApiServiceAthkarRepository(conext)

        }
        fun get(): ApiServiceAthkarRepository {

            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }

    }












}