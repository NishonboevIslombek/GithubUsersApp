package com.example.assignment.di.module

import com.example.assignment.data.networks.repository.NetworkRepositoryImpl
import com.example.assignment.data.users.repository.UsersRepositoryImpl
import com.example.assignment.domain.networks.repository.NetworkRepository
import com.example.assignment.domain.users.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository

    @Binds
    fun bindNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository
}