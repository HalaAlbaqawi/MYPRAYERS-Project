package com.example.prayerproject.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerproject.model.PrayerModel
import com.example.prayerproject.model.PrayersModel
import com.example.prayerproject.repositories.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val homeLiveData = MutableLiveData<PrayerModel>()
    val homeErrorLiveData = MutableLiveData<String>()


    fun callData(lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getData(lat, long)

                response.body()?.run {
                    homeLiveData.postValue(this)
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                homeErrorLiveData.postValue(e.message.toString())
            }
        }

    }
}