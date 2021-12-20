package com.example.prayerproject.api

import retrofit2.http.GET
import retrofit2.http.Query

interface QiblaApi {

@GET("/v1/qibla/25.4106386/51.1846025")
suspend fun getQiblaDirection(

    @Query("direction") direction: Double,
    @Query("latitude") lat: Double,
    @Query("longitude") lon: Double,
)
}