package com.example.assignment.domain.users.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersResponse(
    @Json(name = "items") val items: List<UserItem>
)

@JsonClass(generateAdapter = true)
data class UserItem(
    @Json(name = "id") val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "type") val type: String,
)

@JsonClass(generateAdapter = true)
data class SingleUserItem(
    @Json(name = "id") val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "type") val type: String,
    @Json(name = "followers") val followers: Int,
    @Json(name = "public_gists") val gists: Int,
    @Json(name = "public_repos") val repos: Int,
    @Json(name = "created_at") val createdAt: String
)
