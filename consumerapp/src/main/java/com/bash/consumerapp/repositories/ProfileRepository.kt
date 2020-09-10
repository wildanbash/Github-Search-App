package com.bash.consumerapp.repositories

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.models.UserSearch
import com.bash.consumerapp.network.api.ApiClient
import com.bash.consumerapp.util.USER_CONTENT_URI
import com.bash.consumerapp.util.toContentValues
import com.bash.consumerapp.util.toUserDetail
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {

    private val api = ApiClient.service
    val showProgress = MutableLiveData<Boolean>()
    val userDetail = MutableLiveData<UserDetail>()
    val followers = MutableLiveData<List<UserSearch>>()
    val following = MutableLiveData<List<UserSearch>>()
    val detailUserFavorite = MutableLiveData<UserDetail>()

    fun getDetail(username: String){
        showProgress.value = true
        val call = api.getDetail(username)
        call.enqueue(object : Callback<UserDetail> {
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                showProgress.postValue(false)
                Log.d("Detail", "Failed : ${t.message}")
            }

            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if(response.isSuccessful){
                    Log.d("Detail", "Detail : ${Gson().toJson(response.body())}")
                    showProgress.postValue(false)
                    userDetail.postValue(response.body())
                }
            }
        })
    }

    fun getFollowing(username: String){
        showProgress.value = true
        val call = api.getFollowing(username)
        call.enqueue(object : Callback<List<UserSearch>>{
            override fun onFailure(call: Call<List<UserSearch>>, t: Throwable) {
                showProgress.postValue(false)
                Log.d("Detail", "Failed Following : ${t.message}")
            }

            override fun onResponse(
                call: Call<List<UserSearch>>,
                response: Response<List<UserSearch>>
            ) {
                if(response.isSuccessful){
                    Log.d("Detail", "Following : ${Gson().toJson(response.body())}" )
                    following.postValue(response.body())
                    showProgress.postValue(false)
                }
            }
        })
    }

    fun getFollower(username: String){
        showProgress.value = true
        val call = api.getFollowers(username)
        call.enqueue(object : Callback<List<UserSearch>>{
            override fun onFailure(call: Call<List<UserSearch>>, t: Throwable) {
                showProgress.postValue(false)
                Log.d("Detail", "Failed Followers : ${t.message}")
            }

            override fun onResponse(
                call: Call<List<UserSearch>>,
                response: Response<List<UserSearch>>
            ) {
                if(response.isSuccessful){
                    Log.d("Detail", "Follower ${Gson().toJson(response.body())}")
                    followers.postValue(response.body())
                    showProgress.postValue(false)
                }
            }
        })
    }

    fun insertFavorite(user: UserDetail, context: Context){
        context.contentResolver.insert(USER_CONTENT_URI.toUri(), user.toContentValues())
    }

    fun deleteFavorite(id: Int, context: Context){
        val uri = "$USER_CONTENT_URI/$id".toUri()

        context.contentResolver.delete(uri, null, null)
    }

    fun getUserCheckFavorite(id: Int, context: Context): LiveData<UserDetail> {
        val uri = "$USER_CONTENT_URI/$id".toUri()
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            if(it.moveToFirst()){
                detailUserFavorite.postValue(it.toUserDetail())
                cursor.close()
            }
        }
        return detailUserFavorite
    }

}
