package com.example.prayerproject.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerproject.model.DuaaModel
import com.example.prayerproject.repositories.ApiServiceDuaaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyFavoriteDuaaViewModel"

class MyFavoriteDuaaViewModel : ViewModel() {


    private val apiRepo = ApiServiceDuaaRepository.get()


    val myDuaaLiveData = MutableLiveData<List<DuaaModel>>()
    val myDuaaErrorLiveData = MutableLiveData<String>()
    val LiveData = MutableLiveData<String>()

    fun callData() {
        Log.d(TAG, "Call Data")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getMyDuaa(FirebaseAuth.getInstance().currentUser!!.uid)

                response.body()?.run {
                    myDuaaLiveData.postValue(this.distinctBy { it.title })
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myDuaaErrorLiveData.postValue(e.message.toString())
            }
        }

    }


    fun editDuaa(duaaModel: DuaaModel) {

        Log.d(TAG, "edit Athkar")
        Log.d(TAG, duaaModel.toString())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.editDuaa(duaaModel)

                response.body()?.run {
                    LiveData.postValue(this.toString())
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myDuaaErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    fun deleteDuaa(id: String) {

        Log.d(TAG, "delete Duaa")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.deleteDuaa(id)

                response.body()?.run {
                    LiveData.postValue(this.toString())
                    Log.d(TAG, this.toString())
                    callData()
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myDuaaErrorLiveData.postValue(e.message.toString())
            }
        }

    }
}
