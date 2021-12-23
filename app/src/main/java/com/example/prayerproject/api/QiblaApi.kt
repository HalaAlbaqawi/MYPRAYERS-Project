package com.example.prayerproject.api

import com.example.prayerproject.model.QiblaModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QiblaApi {


    @GET("/v1/qibla/{lat}/{lon}")
    suspend fun getQiblaDirection(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double
    ): Response<QiblaModel>


}