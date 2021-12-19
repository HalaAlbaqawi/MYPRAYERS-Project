package com.example.prayerproject.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerproject.model.AthkarModel
import com.example.prayerproject.repositories.ApiServiceAthkarRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AthkarViewModel"

class AthkarViewModel : ViewModel() {

    private val apiRepo = ApiServiceAthkarRepository.get()
    val athkarLiveData = MutableLiveData<List<AthkarModel>>()
    val LiveData = MutableLiveData<String>()
    val athkarErrorLiveData = MutableLiveData<String>()


    fun callData() {
        Log.d(TAG, "NO DATA")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getAthkar()

                response.body()?.run {
                    athkarLiveData.postValue(this)
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                athkarErrorLiveData.postValue(e.message.toString())
            }
        }


    }

    fun addAthkar(athkarModel: AthkarModel) {

        Log.d(TAG, "check")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.addAthkar(
                    AthkarModel(
                        athkarModel.athkar, "", athkarModel.title,
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, this.toString())
                        LiveData.postValue("Successful")
                    }
                }
                Log.d(TAG, response.message())

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                LiveData.postValue("UnSucessful")
            }
        }
    }
}