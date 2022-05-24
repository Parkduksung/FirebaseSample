package com.example.firebasesample.data.repo

import com.example.firebasesample.data.model.ArticleModel
import com.google.firebase.database.ChildEventListener

interface FirebaseRepository {

    fun createChatRoom(
        articleModel: ArticleModel,
        callback: (String) -> Unit
    )

    fun setChildEventListener(listener : ChildEventListener)


    fun isLoginUser() : Boolean
}