package com.vegh.mvisample.domain.repository

import com.vegh.mvisample.domain.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}
