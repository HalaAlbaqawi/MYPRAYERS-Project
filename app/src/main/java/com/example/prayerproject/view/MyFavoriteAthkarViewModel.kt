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

private const val TAG = "MyFavoriteAthkarViewMod"

class MyFavoriteAthkarViewModel : ViewModel() {


    private val apiRepo = ApiServiceAthkarRepository.get()


    val myAthkarLiveData = MutableLiveData<List<AthkarModel>>()
    val myAthkarErrorLiveData = MutableLiveData<String>()
    val LiveData = MutableLiveData<String>()

    fun callData() {
        Log.d(TAG, "Call Data")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getMyAthkar(FirebaseAuth.getInstance().currentUser!!.uid)

                response.body()?.run {
                    myAthkarLiveData.postValue(this)
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myAthkarErrorLiveData.postValue(e.message.toString())
            }
        }

    }


    fun getMyAthkar() {

        Log.d(TAG, "get Athkar")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getMyAthkar("")

                response.body()?.run {
                    myAthkarLiveData.postValue(this)
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myAthkarErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    fun editAthkar(athkarModel: AthkarModel) {

        Log.d(TAG, "edit Athkar")
        Log.d(TAG, athkarModel.toString())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.editAthkar(athkarModel)

                response.body()?.run {
                    LiveData.postValue(this.toString())
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myAthkarErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    fun deleteAthkar(id: String) {

        Log.d(TAG, "delete Athkar")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.deleteAthkar(id)

                response.body()?.run {
                    LiveData.postValue(this.toString())
                    Log.d(TAG, this.toString())
                }
                Log.d(TAG, response.message())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                myAthkarErrorLiveData.postValue(e.message.toString())
            }
        }

    }
}
