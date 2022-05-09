package com.example.firebasesample.util

import com.example.firebasesample.App
import com.google.firebase.auth.FirebaseAuth


fun FirebaseAuth.createUserWithEmailAndPassword(
    email: String,
    password: String,
    isCreated: (Boolean) -> Unit
) {
    FirebaseAuth.getInstance(App.getFirebaseApp()).createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            isCreated(it.isSuccessful)
        }
}