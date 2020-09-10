package com.bash.githubsearchuser.ui.favorite

import com.bash.githubsearchuser.db.entities.UserEntity

interface FavoriteAdapterInterface {
    fun click(userEntity: UserEntity)
}