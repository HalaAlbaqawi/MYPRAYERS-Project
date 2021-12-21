package com.example.prayerproject.api

import com.example.prayerproject.model.QiblaModel
import retrofit2.Call
import retrofit2.http.GET

interface QiblaApi {


    @GET("/v1/qibla/25.4106386/51.1846025")
    fun getQiblaDirection() : Call<QiblaModel>

}