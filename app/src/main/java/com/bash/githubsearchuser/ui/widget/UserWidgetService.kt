package com.bash.githubsearchuser.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class UserWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        UserWidgetRemoteViewsFactory(this.applicationContext)
}