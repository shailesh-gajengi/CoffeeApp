package com.example.coffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.api.ChatApi
import com.example.coffeeapp.api.CoffeeApi
import com.example.coffeeapp.dto.ChatRequest
import com.example.coffeeapp.dto.ChatResponse
import com.example.coffeeapp.dto.CoffeeResponse
import com.example.coffeeapp.model.ChatMessage
import com.example.coffeeapp.network.RetrofitInstance
import com.example.coffeeapp.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val chatApi =
        RetrofitInstance.retrofit.create(ChatApi::class.java)

    private val coffeeApi =
        RetrofitInstance.retrofit.create(CoffeeApi::class.java)

    private val repository =
        ChatRepository(chatApi)

    private val _messages =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages: StateFlow<List<ChatMessage>>
        get() = _messages


    fun sendMessage(
        userId: String,
        message: String
    ) {

        _messages.value =
            _messages.value + ChatMessage(
                message = message,
                isUser = true
            )

        viewModelScope.launch {

            try {

                val request =
                    ChatRequest(
                        userId = userId,
                        message = message
                    )

                val response =
                    repository.sendMessage(request)

                if (response.isSuccessful) {

                    val body: ChatResponse? =
                        response.body()

                    if (body != null) {

                        val coffees =
                            getRecommendedCoffees(
                                body.recommendedCoffeeIds
                            )

                        _messages.value =
                            _messages.value + ChatMessage(
                                message = body.reply,
                                isUser = false,
                                recommendedCoffees = coffees
                            )
                    }

                } else {

                    _messages.value =
                        _messages.value + ChatMessage(
                            message = "Something went wrong.",
                            isUser = false
                        )
                }

            } catch (e: Exception) {

                e.printStackTrace()

                _messages.value =
                    _messages.value + ChatMessage(
                        message = e.message
                            ?: "Unable to connect to server.",
                        isUser = false
                    )
            }
        }
    }


    private suspend fun getRecommendedCoffees(
        ids: List<Long>
    ): List<CoffeeResponse> {

        val coffees =
            mutableListOf<CoffeeResponse>()

        for (id in ids) {

            try {

                val response =
                    coffeeApi.getCoffeeById(id)

                if (response.isSuccessful) {

                    response.body()?.let {
                        coffees.add(it)
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }

        return coffees
    }
}