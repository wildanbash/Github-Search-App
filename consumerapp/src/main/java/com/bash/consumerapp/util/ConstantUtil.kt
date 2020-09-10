package com.bash.consumerapp.util

// Base Url
const val BASE_URL = "https://api.github.com/"

// Token
const val ACCESS_TOKEN = "148c5ca61ac93bdc8d4cfa37d02c6e26ec68694e"

/* --- URI for ContentProvider --- */
const val URI_AUTHORITY = "com.bash.githubsearchuser"
const val SCHEME = "content"

/* --- Database --- */
const val USER_TABLE_NAME = "users_favorite"
const val USER_CONTENT_URI = "$SCHEME://$URI_AUTHORITY/$USER_TABLE_NAME"

const val USER_ID = "id"
const val USER_AVATAR_URL = "avatar_url"
const val USER_BIO = "bio"
const val USER_BLOG = "blog"
const val USER_COMPANY = "company"
const val USER_CREATED_AT = "created_at"
const val USER_EMAIL = "email"
const val USER_EVENTS_URL = "events_url"
const val USER_FOLLOWERS = "followers"
const val USER_FOLLOWERS_URL = "followers_url"
const val USER_FOLLOWING = "following"
const val USER_FOLLOWING_URL = "following_url"
const val USER_GISTS_URL = "gists_url"
const val USER_GRAVATAR_ID = "gravatar_id"
const val USER_HIREABLE = "hireable"
const val USER_HTML_URL = "html_url"
const val USER_LOCATION = "location"
const val USER_LOGIN = "login"
const val USER_NAME = "name"
const val USER_NODE_ID = "node_id"
const val USER_ORGANIZATIONS_URL = "organizations_url"
const val USER_PUBLIC_GISTS = "public_gists"
const val USER_PUBLIC_REPOS = "public_repos"
const val USER_RECEIVED_EVENTS_URL = "received_events_url"
const val USER_REPOS_URL = "repos_url"
const val USER_SITE_ADMIN = "site_admin"
const val USER_STARRED_URL = "starred_url"
const val USER_SUBSCRIPTIONS_URL = "subscriptions_url"
const val USER_TWITTER_USERNAME = "twitter_username"
const val USER_TYPE = "type"
const val USER_UPDATED_AT = "updated_at"
const val USER_URL = "url"