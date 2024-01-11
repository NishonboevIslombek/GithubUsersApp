package com.example.assignment.domain.users.use_case

import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.model.UserItem

interface GetUserByLoginUseCase {
    suspend operator fun invoke(login: String): Resource<SingleUserItem>
}