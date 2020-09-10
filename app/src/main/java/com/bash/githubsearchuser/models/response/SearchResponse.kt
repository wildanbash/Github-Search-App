package com.bash.githubsearchuser.models.response

import com.bash.githubsearchuser.models.UserSearch
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val items: List<UserSearch>,
    @SerializedName("total_count")
    val total_count: Int
)