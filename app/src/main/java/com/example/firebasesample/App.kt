package com.example.firebasesample

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        fun getFirebaseApp() = FirebaseApp.getInstance()
    }
}