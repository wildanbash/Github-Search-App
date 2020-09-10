package com.bash.githubsearchuser.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.repositories.FavoriteRepository

class FavoriteViewModel : ViewModel(){

    private val repository: FavoriteRepository = FavoriteRepository()
    val favoriteUserList: LiveData<List<UserEntity>>

    init {
        favoriteUserList = repository.favoriteUserList
    }

    fun getFavoriteUserList(context: Context): LiveData<List<UserEntity>> = repository.getFavoriteUserList(context)
}