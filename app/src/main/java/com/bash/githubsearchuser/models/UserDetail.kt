package com.bash.githubsearchuser.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatar_url: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("blog")
    val blog: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("events_url")
    val events_url: String?,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("followers_url")
    val followers_url: String?,
    @SerializedName("following")
    val following: Int,
    @SerializedName("following_url")
    val following_url: String?,
    @SerializedName("gists_url")
    val gists_url: String?,
    @SerializedName("gravatar_id")
    val gravatar_id: String?,
    @SerializedName("hireable")
    val hireable: String?,
    @SerializedName("html_url")
    val html_url: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("node_id")
    val node_id: String?,
    @SerializedName("organizations_url")
    val organizations_url: String?,
    @SerializedName("public_gists")
    val public_gists: Int,
    @SerializedName("public_repos")
    val public_repos: Int,
    @SerializedName("received_events_url")
    val received_events_url: String?,
    @SerializedName("repos_url")
    val repos_url: String?,
    @SerializedName("site_admin")
    val site_admin: Boolean,
    @SerializedName("starred_url")
    val starred_url: String?,
    @SerializedName("subscriptions_url")
    val subscriptions_url: String?,
    @SerializedName("twitter_username")
    val twitter_username: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updated_at")
    val updated_at: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable