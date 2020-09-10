package com.bash.githubsearchuser.util

// Base Url
const val BASE_URL = "https://api.github.com/"

// Token
const val ACCESS_TOKEN = "148c5ca61ac93bdc8d4cfa37d02c6e26ec68694e"

/* --- Alarm --- */
const val ALARM_ID_REPEATING = 100
const val ALARM_CHANNEL_ID = "channel_reminder"
const val ALARM_CHANNEL_NAME = "Reminder Channel"
const val ALARM_TITTLE = "alarm_tittle"
const val ALARM_MESSAGE = "alarm_message"

/* --- SharedPreferences --- */
const val SP_NAME = "sharedPreferencesApps"
const val SP_KEY_REMINDER = "isAlarmEnabled"

/* --- URI for ContentProvider --- */
const val URI_AUTHORITY = "com.bash.githubsearchuser"
const val SCHEME = "content"

/* --- Database --- */
const val USER_TABLE_NAME = "users_favorite"
const val USER_CONTENT_URI = "$SCHEME://$URI_AUTHORITY/$USER_TABLE_NAME"

//favorite_users columns
const val COLUMN_avatar_url = "avatar_url"
const val COLUMN_bio = "bio"
const val COLUMN_blog = "blog"
const val COLUMN_company = "company"
const val COLUMN_created_at = "created_at"
const val COLUMN_email = "email"
const val COLUMN_events_url = "events_url"
const val COLUMN_followers = "followers"
const val COLUMN_followers_url = "followers_url"
const val COLUMN_following = "following"
const val COLUMN_following_url = "following_url"
const val COLUMN_gists_url = "gists_url"
const val COLUMN_gravatar_id = "gravatar_id"
const val COLUMN_hireable = "hireable"
const val COLUMN_html_url = "html_url"
const val COLUMN_id = "id"
const val COLUMN_location = "location"
const val COLUMN_login = "login"
const val COLUMN_name = "name"
const val COLUMN_node_id = "node_id"
const val COLUMN_organizations_url = "organizations_url"
const val COLUMN_public_gists = "public_gists"
const val COLUMN_public_repos = "public_repos"
const val COLUMN_received_events_url = "received_events_url"
const val COLUMN_repos_url = "repos_url"
const val COLUMN_site_admin = "site_admin"
const val COLUMN_starred_url = "starred_url"
const val COLUMN_subscriptions_url = "subscriptions_url"
const val COLUMN_twitter_username = "twitter_username"
const val COLUMN_type = "type"
const val COLUMN_updated_at = "updated_at"
const val COLUMN_url = "url"