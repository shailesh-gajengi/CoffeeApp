package com.example.coffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _user =
        MutableStateFlow<FirebaseUser?>(repository.currentUser())

    val user: StateFlow<FirebaseUser?>
        get() = _user

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    // REGISTER
    fun register(
        name: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                _registerSuccess.value = false
                _error.value = null

                repository.register(
                    name = name,
                    email = email,
                    password = password
                )

                _user.value = repository.currentUser()

                // Only true when Firebase registration succeeded
                _registerSuccess.value = true

            } catch (e: Exception) {

                _registerSuccess.value = false

                _error.value = when {
                    e.message?.contains("email address is already in use", true) == true ->
                        "Email is already registered"

                    e.message?.contains("badly formatted", true) == true ->
                        "Invalid email address"

                    e.message?.contains("password", true) == true ->
                        "Password must be at least 6 characters"

                    else ->
                        "Registration failed. Please try again."
                }
            }
        }
    }


    // LOGIN
    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                repository.login(email, password)

                _user.value = repository.currentUser()

                _loginSuccess.value = true
                _error.value = null

            } catch (e: Exception) {

                _loginSuccess.value = false
                _error.value = e.message
            }
        }
    }


    // LOGOUT
    fun logout() {

        repository.logout()

        _user.value = null
        _loginSuccess.value = false
        _registerSuccess.value = false
    }
}