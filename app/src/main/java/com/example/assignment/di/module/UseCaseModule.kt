package com.example.assignment.di.module

import com.example.assignment.data.networks.use_case.CheckInternetConnectionUseCaseImpl
import com.example.assignment.data.users.use_case.GetUserByLoginUseCaseImpl
import com.example.assignment.data.users.use_case.GetUsersByUsernameUseCaseImpl
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import com.example.assignment.domain.users.use_case.GetUserByLoginUseCase
import com.example.assignment.domain.users.use_case.GetUsersByUsernameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun bindGetUsersByUsernameUseCase(impl: GetUsersByUsernameUseCaseImpl): GetUsersByUsernameUseCase

    @Binds
    fun bindGetUserByLoginUseCase(impl: GetUserByLoginUseCaseImpl): GetUserByLoginUseCase

    @Binds
    fun bindCheckInternetConnectionUseCase(impl: CheckInternetConnectionUseCaseImpl): CheckInternetConnectionUseCase
}