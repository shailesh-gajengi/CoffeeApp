package com.example.coffeeapp.repository

import com.example.coffeeapp.api.ChatApi
import com.example.coffeeapp.dto.ChatRequest
import com.example.coffeeapp.dto.ChatResponse
import retrofit2.Response

class ChatRepository(
    private val api: ChatApi
) {

    suspend fun sendMessage(
        request: ChatRequest
    ): Response<ChatResponse> {
        return api.chat(request)
    }
}