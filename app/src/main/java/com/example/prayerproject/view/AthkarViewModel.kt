package com.example.prayerproject.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prayerproject.api.PrayersApi
import com.example.prayerproject.model.PrayerModel
import com.example.prayerproject.model.PrayersModel
import com.example.prayerproject.repositories.ApiServiceRepository

private const val TAG = "AthkarViewModel"
class AthkarViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val athkarLiveData = MutableLiveData<PrayerModel>()
    val athkarErrorLiveData = MutableLiveData<String>()



}