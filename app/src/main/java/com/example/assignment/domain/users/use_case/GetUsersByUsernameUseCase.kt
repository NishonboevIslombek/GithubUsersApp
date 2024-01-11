package com.example.assignment.domain.users.use_case

import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.UserItem

interface GetUsersByUsernameUseCase {
    suspend operator fun invoke(username: String): Resource<List<UserItem>>
}