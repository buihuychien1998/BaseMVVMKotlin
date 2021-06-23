package com.example.basemvvm.data.repository

import com.example.basemvvm.data.ApiService
import com.example.basemvvm.model.User
import javax.inject.Inject
import kotlin.coroutines.Continuation

class MainRepository @Inject constructor(private val apiService: ApiService) : MainDataSource {
    override suspend fun getUsers() = apiService.getUsers()
}