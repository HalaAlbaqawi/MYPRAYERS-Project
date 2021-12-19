package com.example.prayerproject.api

import com.example.prayerproject.model.AthkarModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AthkarApi {

    @GET("/Duaa")
    suspend fun getAthkar (
    ): Response<List<AthkarModel>>


    @GET("/myduaa")
    suspend fun getMyAthkar ( @Query("userid") userid: String
    ): Response<List<AthkarModel>>


    @POST("/myduaa")
    suspend fun addAthkar(@Body AktharBody: AthkarModel): Response<ResponseBody>


    @PUT("/myduaa/{id}")
    suspend fun editAthkar(@Path("id") id: String,
    @Body AktharBody: AthkarModel): Response<ResponseBody>


    @DELETE("/myduaa/{id}")
    suspend fun deleteAthkar(@Path("id") Id: String): Response<ResponseBody>

}