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

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<GithubUser>>()

    fun setListFollowing(username: String){
        val client = ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<GithubUser>>{
                override fun onResponse(
                    call: Call<ArrayList<GithubUser>>,
                    response: Response<ArrayList<GithubUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }}

                override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getListFollowing(): LiveData<ArrayList<GithubUser>>{
        return listFollowing
    }
}