package com.bash.githubsearchuser.db.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import com.bash.githubsearchuser.db.AppDatabase
import com.bash.githubsearchuser.ui.widget.UserWidget
import com.bash.githubsearchuser.util.URI_AUTHORITY
import com.bash.githubsearchuser.util.USER_CONTENT_URI
import com.bash.githubsearchuser.util.USER_TABLE_NAME
import com.bash.githubsearchuser.util.toUserEntity

class UserContentProvider : ContentProvider(){

    companion object {

        private const val USER = 1
        private const val USER_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(URI_AUTHORITY, USER_TABLE_NAME, USER)

            uriMatcher.addURI(URI_AUTHORITY, "$USER_TABLE_NAME/#", USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val userDao = context?.let { AppDatabase.getDatabase(it).userDao() }

        return when(uriMatcher.match(uri)){
            USER -> userDao?.getAllUser()
            USER_ID ->uri.lastPathSegment?.toInt()?.let { userDao?.getDetailUser(it) }
            else -> null
        }

    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val userDao = context?.let { AppDatabase.getDatabase(it).userDao() }

        val id = when (uriMatcher.match(uri)){
            USER -> {
                values?.let {
                    userDao?.insertUser(it.toUserEntity())
                } ?: 0
            }
            else -> 0
        }

        context?.contentResolver?.notifyChange(USER_CONTENT_URI.toUri(), null)

        context?.let { refreshWidgetUser(it) }

        return Uri.parse("$USER_CONTENT_URI/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val userDao = context?.let { AppDatabase.getDatabase(it).userDao() }

        val count: Int = when(uriMatcher.match(uri)){
            USER_ID -> {
                uri.lastPathSegment?.toInt()?.let {
                    userDao?.deleteUser(it)
                } ?: 0
            } else -> 0
        }

        context?.contentResolver?.notifyChange(USER_CONTENT_URI.toUri(), null)

        context?.let { refreshWidgetUser(it) }

        return count
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selecttionArgs: Array<String>?
    ): Int {
        return 0
    }

    private fun refreshWidgetUser(context: Context) {
        UserWidget.sendRefreshBroadcast(context)
    }
}