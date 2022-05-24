package com.example.firebasesample.di

import com.example.firebasesample.data.repo.FirebaseRepository
import com.example.firebasesample.data.repo.FirebaseRepositoryImpl
import com.example.firebasesample.data.source.FirebaseRemoteDataSource
import com.example.firebasesample.data.source.FirebaseRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Singleton
    @Binds
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): FirebaseRemoteDataSource
}