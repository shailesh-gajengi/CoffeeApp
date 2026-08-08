package com.example.coffeeapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    suspend fun register(
        name: String,
        email: String,
        password: String
    ) {

        // Create Firebase account
        auth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        // Save user's name in Firebase
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        auth.currentUser
            ?.updateProfile(profileUpdates)
            ?.await()
    }

    suspend fun login(
        email: String,
        password: String
    ) =
        auth.signInWithEmailAndPassword(
            email,
            password
        ).await()

    fun logout() {
        auth.signOut()
    }

    fun currentUser() =
        auth.currentUser
}