package com.example.basemvvm.data.repository

import com.example.basemvvm.model.User

interface MainDataSource {
    suspend fun getUsers(): List<User>
}