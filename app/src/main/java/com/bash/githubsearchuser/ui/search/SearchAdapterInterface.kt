package com.bash.githubsearchuser.ui.search

import com.bash.githubsearchuser.models.UserSearch

interface SearchAdapterInterface {
    fun click(userSearch: UserSearch)
}