package com.example.prayerproject.api

import com.example.prayerproject.model.AthkarModel
import com.example.prayerproject.model.PrayerModel
import com.example.prayerproject.model.PrayersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PrayersApi {


@GET("/v1/timings?method=4")
suspend fun getData (

    @Query("latitude") lat: Double,
    @Query("longitude") lon: Double,


    ): Response<PrayerModel>


    @GET("/Duaa")
    suspend fun getAthkar (

        ): Response <List<AthkarModel>>



}