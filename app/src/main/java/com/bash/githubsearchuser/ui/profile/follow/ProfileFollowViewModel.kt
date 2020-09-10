package com.bash.githubsearchuser.ui.profile.follow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bash.githubsearchuser.db.AppDatabase
import com.bash.githubsearchuser.models.UserSearch
import com.bash.githubsearchuser.repositories.ProfileRepository

class ProfileFollowViewModel(application: Application) : AndroidViewModel(application){
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository : ProfileRepository = ProfileRepository(userDao = userDao)
    val showProgress : LiveData<Boolean>
    val following : LiveData<List<UserSearch>>
    val followers : LiveData<List<UserSearch>>

    init{
        showProgress = repository.showProgress
        following = repository.following
        followers = repository.followers
    }

    fun getFollowing(username: String){
        repository.getFollowing(username = username)
    }

    fun getFollower(username: String){
        repository.getFollower(username = username)
    }
}