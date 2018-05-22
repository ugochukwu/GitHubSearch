package com.onwordiesquire.android.githubsearch.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(@SerializedName("total_count") val total_count: Int,
                             @SerializedName("incomplete_results") val incomplete_results: Boolean,
                             @SerializedName("items") val items: List<Item>)

data class Item(@SerializedName("id") val id: Int,
                @SerializedName("name") val name: String,
                @SerializedName("full_name") val fullName: String,
                @SerializedName("owner") val owner: Owner,
                @SerializedName("description") val description: String,
                @SerializedName("fork") val fork: Boolean,
                @SerializedName("forks_url") val forksUrl: String,
                @SerializedName("keys_url") val keysUrl: String,
                @SerializedName("subscribers_url") val subscribersUrl: String,
                @SerializedName("forks_count") val forksCount: Int,
                @SerializedName("forks") val forks: Int)

data class Owner(@SerializedName("login") val login: String,
                 @SerializedName("id") val id: Int,
                 @SerializedName("avatar_url") val avatarUrl: String,
                 @SerializedName("gravatar_id") val gravatarId: String,
                 @SerializedName("type") val type: String)