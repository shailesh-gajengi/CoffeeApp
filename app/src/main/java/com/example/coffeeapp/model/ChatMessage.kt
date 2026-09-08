package com.example.coffeeapp.model

import com.example.coffeeapp.dto.CoffeeResponse

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val recommendedCoffees: List<CoffeeResponse> = emptyList()
)