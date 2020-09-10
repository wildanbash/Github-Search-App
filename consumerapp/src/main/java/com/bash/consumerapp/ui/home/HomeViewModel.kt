package com.bash.consumerapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.repositories.FavoriteRepository

class HomeViewModel: ViewModel(){
    private val repository: FavoriteRepository = FavoriteRepository()
    val favoriteUserList: LiveData<List<UserDetail>>

    init {
        favoriteUserList = repository.favoriteUserList
    }

    fun getFavoriteUsersList(context: Context): LiveData<List<UserDetail>> = repository.getFavoriteUserList(context)
}