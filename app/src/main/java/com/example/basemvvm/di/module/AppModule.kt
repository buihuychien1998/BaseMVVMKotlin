package com.example.basemvvm.di.module

import com.example.basemvvm.data.repository.MainDataSource
import com.example.basemvvm.data.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideAuthDataSource(mainRepository: MainRepository): MainDataSource
}