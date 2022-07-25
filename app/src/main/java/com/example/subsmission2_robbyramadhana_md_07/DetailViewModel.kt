package com.example.subsmission2_robbyramadhana_md_07

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val retrofit = RetrofitService.create()
    private val user = MutableLiveData<Desc<UserData>>()

    fun getDetailUser(username: String?): LiveData<Desc<UserData>> {
        retrofit.getDetailUser(username!!).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                val result = response.body()
                user.postValue(Desc.Success(result))
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }
        })
        return user
    }
}