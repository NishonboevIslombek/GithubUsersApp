package com.example.assignment.domain.networks.repository

interface NetworkRepository {
    fun hasInternetConnection(): Boolean
}