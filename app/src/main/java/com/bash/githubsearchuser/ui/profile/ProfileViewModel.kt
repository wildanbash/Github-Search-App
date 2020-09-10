package com.bash.githubsearchuser.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bash.githubsearchuser.db.AppDatabase
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.models.UserDetail
import com.bash.githubsearchuser.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application){
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository: ProfileRepository = ProfileRepository(userDao = userDao)
    val showProgress : LiveData<Boolean>
    val userDetail : LiveData<UserDetail>
    val detailUserFavorite : LiveData<UserEntity>

    init {
        showProgress = repository.showProgress
        userDetail = repository.userDetail
        detailUserFavorite = repository.detailUserFavorite
    }

    fun getDetail(username: String) = repository.getDetail(username)

    fun insertFavoriteUsers(user: UserEntity, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(user, context)
        }
    }

    fun deleteUser(id: Int, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(id, context)
        }
    }

    fun getUserCheckFavorite(id: Int, context: Context){
        repository.getDetailUserFavorite(id, context)
    }

}