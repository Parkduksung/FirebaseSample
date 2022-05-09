package com.example.firebase

import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseRemoteDataSource<B : FirebaseData> {

    fun createUser(email: String, password: String, isCreated: (Boolean) -> Unit)

    fun addData(collection: String, data: B, isAdd: (Boolean) -> Unit)
}


object FirebaseStore {
    inline fun <reified D> addData(collection: String, crossinline isAdd: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance().collection(collection).document().set(D::class.java)
            .addOnCompleteListener {
                isAdd(it.isSuccessful)
            }
    }
}