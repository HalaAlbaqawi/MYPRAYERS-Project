package com.example.prayerproject.api

import com.example.prayerproject.model.AthkarModel
import retrofit2.Response
import retrofit2.http.GET

interface AthkarApi {

    @GET("/Duaa")
    suspend fun getAthkar (
    ): Response<List<AthkarModel>>

}