package com.example.assignment.data.users.network

import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {

    @GET("search/users")
    suspend fun getUsersByUsername(@Query("q") username: String): UsersResponse

    @GET("users/{login}")
    suspend fun getUserByLogin(@Path("login") login: String): SingleUserItem

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}