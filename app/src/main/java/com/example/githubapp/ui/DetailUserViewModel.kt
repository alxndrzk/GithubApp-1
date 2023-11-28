package com.example.githubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val userDetailLiveData = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = userDetailLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingLiveData

    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    fun fetchUserDetails(username: String) {
        isLoadingLiveData.value = true
        val client = ApiConfig.getApiService().getDetailUser(username) //mendapatkan detail pengguna dari github API
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                isLoadingLiveData.value = false
                if (response.isSuccessful){
                    userDetailLiveData.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                isLoadingLiveData.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}