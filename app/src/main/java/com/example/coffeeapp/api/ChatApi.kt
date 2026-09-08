package com.example.coffeeapp.api

import com.example.coffeeapp.dto.ChatRequest
import com.example.coffeeapp.dto.ChatResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {

    @POST("api/chat")
    suspend fun chat(
        @Body request: ChatRequest
    ): Response<ChatResponse>
}