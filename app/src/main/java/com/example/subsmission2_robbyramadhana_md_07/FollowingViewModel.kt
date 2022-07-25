package com.example.subsmission2_robbyramadhana_md_07

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val retrofit = RetrofitService.create()
    private val listUser = MutableLiveData<Desc<List<UserData>>>()

    fun getUserFollowing(username: String): LiveData<Desc<List<UserData>>> {
        listUser.postValue(Desc.Loading())
        retrofit.getUserFollowing(username).enqueue(object : Callback<List<UserData>> {
            override fun onResponse(call: Call<List<UserData>>, response: Response<List<UserData>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Desc.Error(null))
                else
                    listUser.postValue(Desc.Success(list))
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                listUser.postValue(Desc.Error(t.message))
            }
        })
        return listUser
    }
}