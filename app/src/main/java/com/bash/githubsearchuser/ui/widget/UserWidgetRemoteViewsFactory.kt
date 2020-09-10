package com.bash.githubsearchuser.ui.widget

import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.net.toUri
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.util.USER_CONTENT_URI
import com.bash.githubsearchuser.util.toBitmap
import com.bash.githubsearchuser.util.toListUserEntity

internal class UserWidgetRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list: List<UserEntity> = listOf()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {

        cursor?.close()

        val identityToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver?.query(USER_CONTENT_URI.toUri(), null, null, null, null)
        cursor?.let {
            list = it.toListUserEntity()
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        cursor?.close()
        list = listOf()
    }

    override fun getCount(): Int = list.size

    override fun getViewAt(position: Int): RemoteViews? {

        val views = RemoteViews(mContext.packageName, R.layout.user_widget_item)

        if (!list.isNullOrEmpty()) {
            // Set item stack view
            views.apply {
                list[position].apply {
                    setImageViewBitmap(
                        R.id.userWidgetItem_img_user, avatar_url?.toBitmap(mContext)
                    )
                    setTextViewText(
                        R.id.userWidgetItem_tv_name, name ?: login
                    )
                    setTextViewText(
                        R.id.userWidgetItem_tv_username, if (!name.isNullOrEmpty()) login else type
                    )
                }
            }
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = true
}
