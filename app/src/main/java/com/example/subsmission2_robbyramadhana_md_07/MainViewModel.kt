package com.example.subsmission2_robbyramadhana_md_07

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val retrofit: ApiService = RetrofitService.create()
    private val listUser = MutableLiveData<Desc<List<UserData>>>()

    fun searchUser(query: String): LiveData<Desc<List<UserData>>> {
        listUser.postValue(Desc.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchList> {
            override fun onResponse(
                call: Call<SearchList>,
                response: Response<SearchList>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Desc.Error(null))
                else
                    listUser.postValue(Desc.Success(list))
            }

            override fun onFailure(call: Call<SearchList>, t: Throwable) {
                listUser.postValue(Desc.Error(t.message))
            }
        })
        return listUser
    }
}