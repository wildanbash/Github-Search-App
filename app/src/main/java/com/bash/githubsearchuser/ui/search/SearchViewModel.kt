package com.bash.githubsearchuser.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bash.githubsearchuser.models.UserSearch
import com.bash.githubsearchuser.repositories.SearchRepository

class SearchViewModel : ViewModel(){

    private val repository = SearchRepository()
    val showProgress : LiveData<Boolean>
    val userList : LiveData<List<UserSearch>>

    init{
        this.showProgress = repository.showProgress
        this.userList = repository.userList
    }

    fun getSearchUser(keyword: String) = repository.getUserSearch(keyword)
}