package com.bash.githubsearchuser.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bash.githubsearchuser.models.UserSearch
import com.bash.githubsearchuser.models.response.SearchResponse
import com.bash.githubsearchuser.network.api.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository{
    private val api = ApiClient.service
    val showProgress = MutableLiveData<Boolean>()
    val userList = MutableLiveData<List<UserSearch>>()

    fun getUserSearch(keyword: String){
        showProgress.value = true
        val call = api.getSearchResult(keyword)
        call.enqueue(object : Callback<SearchResponse>{
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showProgress.postValue(false)
                Log.d("SearchUser", "Failed : ${t.message}")
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("SearchUser", Gson().toJson(response.body()))
                    userList.postValue(response.body()?.items)
                    showProgress.postValue(false)
                }
            }
        })
    }
}