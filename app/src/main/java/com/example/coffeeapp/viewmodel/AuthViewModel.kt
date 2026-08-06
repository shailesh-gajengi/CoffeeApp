package com.example.coffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val repository = AuthRepository()

    private val _user =
        MutableStateFlow<FirebaseUser?>(repository.currentUser())

    val user: StateFlow<FirebaseUser?>
        get() = _user

    fun register(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                repository.register(
                    email,
                    password
                )

                _user.value =
                    repository.currentUser()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

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

    fun logout() {

        repository.logout()

        _user.value = null

    }
}