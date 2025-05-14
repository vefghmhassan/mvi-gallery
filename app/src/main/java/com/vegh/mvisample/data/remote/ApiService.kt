package com.vegh.mvisample.data.remote

import com.vegh.mvisample.domain.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}