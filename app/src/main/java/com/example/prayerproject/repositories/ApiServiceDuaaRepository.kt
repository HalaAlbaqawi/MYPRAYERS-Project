package com.example.prayerproject.repositories

import android.content.Context
import com.example.prayerproject.api.DuaaApi
import com.example.prayerproject.model.DuaaModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://61ae0fdca7c7f3001786f5c4.mockapi.io"

class ApiServiceDuaaRepository(val context: Context) {


    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //API FOR RETROFIT
    private val retrofitApi = retrofitService.create(DuaaApi::class.java)


    suspend fun getDuaa() = retrofitApi.getDuaa()
    suspend fun getMyDuaa(userid: String) = retrofitApi.getMyDuaa(userid)
    suspend fun addDuaa(duaaModel: DuaaModel) = retrofitApi.addDuaa(duaaModel)
    suspend fun editDuaa(duaaModel: DuaaModel) =
        retrofitApi.editDuaa(duaaModel.id, duaaModel)

    suspend fun deleteDuaa(id: String) = retrofitApi.deleteDuaa(id)

    companion object {
        private var instance: ApiServiceDuaaRepository? = null

        // design pattern
        fun init(conext: Context) {

            if (instance == null)
                instance = ApiServiceDuaaRepository(conext)

        }

        fun get(): ApiServiceDuaaRepository {

            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }

    }

}