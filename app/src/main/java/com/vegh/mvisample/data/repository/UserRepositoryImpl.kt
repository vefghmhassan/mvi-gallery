package com.vegh.mvisample.data.repository

import com.vegh.mvisample.data.remote.ApiService
import com.vegh.mvisample.domain.User
import com.vegh.mvisample.domain.repository.UserRepository
import jakarta.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return api.getUsers()
    }
}
