package com.example.assignment.data.users.use_case

import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.repository.UsersRepository
import com.example.assignment.domain.users.use_case.GetUserByLoginUseCase
import com.example.assignment.domain.users.use_case.GetUsersByUsernameUseCase
import javax.inject.Inject

class GetUserByLoginUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUserByLoginUseCase {

    override suspend fun invoke(login: String): Resource<SingleUserItem> {
        return when (val response = usersRepository.getUserByLogin(login)) {
            is Resource.Success -> Resource.Success(response.data)
            is Resource.Failed -> Resource.Failed(response.message)
            else -> Resource.Error("Unknown Error")
        }
    }
}