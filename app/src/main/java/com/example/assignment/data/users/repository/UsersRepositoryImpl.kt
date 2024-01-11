package com.example.assignment.data.users.repository

import com.example.assignment.data.users.network.UsersApi
import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.model.UsersResponse
import com.example.assignment.domain.users.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersRepository {
    override suspend fun getUsersByUsername(username: String): Resource<UsersResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Resource.Success(usersApi.getUsersByUsername(username))
            } catch (throwable: Throwable) {
                Resource.Failed(throwable.message ?: "")
            }
        }

    override suspend fun getUserByLogin(login: String): Resource<SingleUserItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Resource.Success(usersApi.getUserByLogin(login))
            } catch (throwable: Throwable) {
                Resource.Failed(throwable.message ?: "")
            }
        }
}