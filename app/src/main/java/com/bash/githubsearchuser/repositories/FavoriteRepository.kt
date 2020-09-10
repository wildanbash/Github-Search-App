package com.bash.githubsearchuser.repositories

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.util.USER_CONTENT_URI
import com.bash.githubsearchuser.util.toListUserEntity

class FavoriteRepository {
    val favoriteUserList = MutableLiveData<List<UserEntity>>()

    fun getFavoriteUserList(context: Context): LiveData<List<UserEntity>>{
        val cursor = context.contentResolver.query(USER_CONTENT_URI.toUri(), null, null, null, null)
        cursor?.let {
            favoriteUserList.postValue(it.toListUserEntity())
            cursor.close()
        }
        return favoriteUserList
    }

}