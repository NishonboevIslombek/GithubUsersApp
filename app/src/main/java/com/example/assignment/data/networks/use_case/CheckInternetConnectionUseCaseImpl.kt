package com.example.assignment.data.networks.use_case

import com.example.assignment.domain.networks.repository.NetworkRepository
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import javax.inject.Inject

class CheckInternetConnectionUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : CheckInternetConnectionUseCase {
    override fun hasInternetConnection(): Boolean {
        return networkRepository.hasInternetConnection()
    }
}