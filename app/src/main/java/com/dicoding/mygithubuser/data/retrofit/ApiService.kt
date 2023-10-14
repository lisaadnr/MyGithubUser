package com.dicoding.mygithubuser.data.retrofit

import com.dicoding.mygithubuser.data.response.DetailUserResponse
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: Bearer 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")
    @GET("search/users")
    fun getUserResponse(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: Bearer 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")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: Bearer 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<GithubUser>>

    @Headers("Authorization: Bearer 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<GithubUser>>

}