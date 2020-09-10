package com.bash.consumerapp.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel(){
    private val repository: ProfileRepository = ProfileRepository()
    val showProgress : LiveData<Boolean>
    val userDetail : LiveData<UserDetail>
    val checkFavorite : LiveData<UserDetail>

    init {
        showProgress = repository.showProgress
        userDetail = repository.userDetail
        checkFavorite = repository.detailUserFavorite
    }

    fun getDetail(username: String) = repository.getDetail(username)

    fun insertFavoriteUsers(user: UserDetail, context: Context){
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
        repository.getUserCheckFavorite(id, context)
    }

}