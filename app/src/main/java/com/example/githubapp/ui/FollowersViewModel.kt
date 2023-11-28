package com.example.githubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.UserFollowerResponseItem
import com.example.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followerlist = MutableLiveData<List<UserFollowerResponseItem>>()
    val follower: LiveData<List<UserFollowerResponseItem>> = _followerlist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val  TAG = "FollowersViewModel"
    }

    fun listFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerResponseItem>>,
                response: Response<List<UserFollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerlist.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowerResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}