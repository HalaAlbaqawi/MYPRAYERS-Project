package com.example.prayerproject.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerproject.model.QiblaModel
import com.example.prayerproject.repositories.ApiServiceQiblaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "QiblaViewModel"
class QiblaViewModel: ViewModel() {

    val qiblaLiveData = MutableLiveData<Double>()
    val qiblaErorrLiveData = MutableLiveData<String>()
    val apiRepo = ApiServiceQiblaRepository.get()


    fun getQibla(lat: Double, lon: Double) {


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getQiblaDirection(lat, lon)

                response.body()?.run {
                    qiblaLiveData.postValue(data.direction)
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                qiblaErorrLiveData.postValue(e.message.toString())
            }
        }


    }


}