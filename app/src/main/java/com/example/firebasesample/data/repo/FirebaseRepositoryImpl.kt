package com.example.firebasesample.data.repo

import com.example.firebasesample.data.model.ArticleModel
import com.example.firebasesample.data.source.FirebaseRemoteDataSource
import com.google.firebase.database.ChildEventListener
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource) :
    FirebaseRepository {

    override fun createChatRoom(articleModel: ArticleModel, callback: (String) -> Unit) {
        firebaseRemoteDataSource.createChatRoom(articleModel, callback)
    }

    override fun setChildEventListener(listener: ChildEventListener) {
        firebaseRemoteDataSource.setChildEventListener(listener)
    }

    override fun isLoginUser(): Boolean =
        firebaseRemoteDataSource.isLoginUser()

    override fun removeChildEventListener(listener: ChildEventListener) {
        firebaseRemoteDataSource.removeChildEventListener(listener)
    }
}