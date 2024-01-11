package com.example.assignment.data.users.use_case

import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.repository.UsersRepository
import com.example.assignment.domain.users.use_case.GetUsersByUsernameUseCase
import javax.inject.Inject

class GetUsersByUsernameUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUsersByUsernameUseCase {

    override suspend fun invoke(username: String): Resource<List<UserItem>> {
        return when (val response = usersRepository.getUsersByUsername(username)) {
            is Resource.Success -> Resource.Success(response.data.items)
            is Resource.Failed -> Resource.Failed(response.message)
            is Resource.Loading -> Resource.Loading(true)
            else -> Resource.Error("Unknown Error")
        }
    }
}