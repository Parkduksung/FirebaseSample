package com.example.firebase

interface FirebaseRepository {

    fun createUser()

    fun deleteUser()

    fun loginUser()

    fun logoutUser()

    fun resetUserPass()

    fun registerAuthListener()

    fun unregisterAuthListener()
}