package com.example.assignment.domain.users.repository

import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.model.UsersResponse

interface UsersRepository {
    suspend fun getUsersByUsername(username: String): Resource<UsersResponse>
    suspend fun getUserByLogin(login: String): Resource<SingleUserItem>
}