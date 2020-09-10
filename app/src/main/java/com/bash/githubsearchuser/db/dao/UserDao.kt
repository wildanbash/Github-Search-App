package com.bash.githubsearchuser.db.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bash.githubsearchuser.db.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users_favorite WHERE id = :id")
    fun getDetailUser(id: Int): Cursor

    @Query("SELECT * FROM users_favorite")
    fun getAllUser(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Long

    @Query("DELETE FROM users_favorite WHERE id = :id")
    fun deleteUser(id: Int): Int

}