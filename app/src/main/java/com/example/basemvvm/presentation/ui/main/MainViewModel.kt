package com.example.basemvvm.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.basemvvm.common.ResultWrapper
import com.example.basemvvm.common.ResultWrapper.Companion.error
import com.example.basemvvm.common.ResultWrapper.Companion.loading
import com.example.basemvvm.common.ResultWrapper.Companion.success
import com.example.basemvvm.data.repository.MainDataSource
import com.example.basemvvm.model.User
import com.example.basemvvm.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(private val mainDataSource: MainDataSource) :
    BaseViewModel() {
//    private val coroutineContext: CoroutineContext
//        get() = Dispatchers.IO + job + exceptionHandler
//
//    private val job: Job = SupervisorJob()
//    val scope = CoroutineScope(coroutineContext)
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        users.postValue(error(exception.localizedMessage))
        Log.d("TAG", ": $exception")
    }
    private val users = MutableLiveData<ResultWrapper<List<User>>>()

//    fun getUsers() = liveData(Dispatchers.IO) {
//        emit(loading())
//        try {
//            emit(success(data = mainDataSource.getUsers()))
//        } catch (exception: Exception) {
//            emit(error(message = exception.message ?: "Error Occurred!"))
//        }
//    }

    fun fetchUsers() {
        viewModelScope.launch(exceptionHandler) {
            users.postValue(loading())
            val usersFromApi = mainDataSource.getUsers()
            users.postValue(success(usersFromApi))
        }
    }

    fun getUsers(): LiveData<ResultWrapper<List<User>>> {
        return users
    }
}