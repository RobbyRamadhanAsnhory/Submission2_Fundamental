package com.example.subsmission2_robbyramadhana_md_07

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers (
        @Query("q")
        query: String
    ): Call<SearchList>

    @GET("users/{username}")
    fun getDetailUser (
        @Path("username")
        username: String
    ): Call<UserData>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username")
        username: String
    ): Call<List<UserData>>

    @GET("users/{username}/following")
    fun getUserFollowing (
        @Path("username")
        username: String
    ): Call<List<UserData>>
}