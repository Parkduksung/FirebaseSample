package com.example.firebasesample.data.source

import com.example.firebasesample.data.model.ArticleModel
import com.google.firebase.database.ChildEventListener

interface FirebaseRemoteDataSource {

    fun createChatRoom(
        articleModel: ArticleModel,
        callback: (String) -> Unit
    )

    fun setChildEventListener(listener : ChildEventListener)
    fun removeChildEventListener(listener: ChildEventListener)

    fun isLoginUser() : Boolean
}