package com.onwordiesquire.android.githubsearch.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(@SerializedName("total_count") val totalCount: Int?,
                             @SerializedName("incomplete_results") val incompleteResults: Boolean?,
                             @SerializedName("items") val items: List<ItemDto>?)

data class ItemDto(@SerializedName("id") val id: Int,
                   @SerializedName("name") val name: String?,
                   @SerializedName("full_name") val fullName: String?,
                   @SerializedName("owner") val owner: OwnerDto?,
                   @SerializedName("description") val description: String?,
                   @SerializedName("fork") val fork: Boolean?,
                   @SerializedName("forks_url") val forksUrl: String?,
                   @SerializedName("keys_url") val keysUrl: String?,
                   @SerializedName("subscribers_url") val subscribersUrl: String?,
                   @SerializedName("forks_count") val forksCount: Int?,
                   @SerializedName("forks") val forks: Int?)

data class OwnerDto(@SerializedName("login") val login: String?,
                    @SerializedName("id") val id: Int?,
                    @SerializedName("avatar_url") val avatarUrl: String?,
                    @SerializedName("gravatar_id") val gravatarId: String?,
                    @SerializedName("type") val type: String?)