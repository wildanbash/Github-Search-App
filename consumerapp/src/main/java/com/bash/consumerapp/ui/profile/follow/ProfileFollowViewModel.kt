package com.bash.consumerapp.ui.profile.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bash.consumerapp.models.UserSearch
import com.bash.consumerapp.repositories.ProfileRepository

class ProfileFollowViewModel: ViewModel(){
    private val repository : ProfileRepository = ProfileRepository()
    val showProgress : LiveData<Boolean>
    val following : LiveData<List<UserSearch>>
    val followers : LiveData<List<UserSearch>>

    init{
        showProgress = repository.showProgress
        following = repository.following
        followers = repository.followers
    }

    fun getFollowing(username: String){
        repository.getFollowing(username)
    }

    fun getFollower(username: String){
        repository.getFollower(username)
    }
}