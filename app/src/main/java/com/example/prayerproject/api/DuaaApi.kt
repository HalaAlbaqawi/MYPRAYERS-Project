package com.example.prayerproject.api

import com.example.prayerproject.model.DuaaModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface DuaaApi {

    @GET("/Duaa")
    suspend fun getAthkar(
    ): Response<List<DuaaModel>>


    @GET("/myduaa")
    suspend fun getMyAthkar(
        @Query("userid") userid: String
    ): Response<List<DuaaModel>>


    @POST("/myduaa")
    suspend fun addAthkar(@Body DuaaBody: DuaaModel): Response<ResponseBody>


    @PUT("/myduaa/{id}")
    suspend fun editAthkar(
        @Path("id") id: String,
        @Body DuaaBody: DuaaModel
    ): Response<ResponseBody>


    @DELETE("/myduaa/{id}")
    suspend fun deleteAthkar(@Path("id") Id: String): Response<ResponseBody>
}