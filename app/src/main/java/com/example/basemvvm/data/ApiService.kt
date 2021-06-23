package com.example.basemvvm.data

import com.example.basemvvm.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}