package com.example.githubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.UserGithubReesponse
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoadingIndicator = MutableLiveData<Boolean>()
    val isLoadingIndicator: LiveData<Boolean> = _isLoadingIndicator

    companion object {
        private const val  TAG = "MainViewModel"
    }

    init {
        findUsers("a")
    }

    fun findUsers(username: String) {

        _isLoadingIndicator.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserGithubReesponse> {
            override fun onResponse(
                call: Call<UserGithubReesponse>,
                response: Response<UserGithubReesponse>
            ) {
                _isLoadingIndicator.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()?.items
                } else {
                    Log.e(TAG,"onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<UserGithubReesponse>, t: Throwable) {
                _isLoadingIndicator.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}