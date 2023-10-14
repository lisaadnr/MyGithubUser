package com.dicoding.mygithubuser.ui.detail

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.data.response.DetailUserResponse
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class FollowersViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<GithubUser>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<GithubUser>>{
                override fun onResponse(
                    call: Call<ArrayList<GithubUser>>,
                    response: Response<ArrayList<GithubUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }}

                override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<GithubUser>>{
        return listFollowers
    }
}