package com.bash.consumerapp.repositories

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.util.USER_CONTENT_URI
import com.bash.consumerapp.util.toListUserDetail

class FavoriteRepository {
    val favoriteUserList = MutableLiveData<List<UserDetail>>()

    fun getFavoriteUserList(context: Context): LiveData<List<UserDetail>>{
        val cursor = context.contentResolver.query(USER_CONTENT_URI.toUri(), null, null, null, null)
        cursor?.let {
            favoriteUserList.postValue(it.toListUserDetail())
            cursor.close()
        }
        return favoriteUserList
    }

}