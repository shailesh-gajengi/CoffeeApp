package com.example.coffeeapp.dto

data class ChatResponse(
    val reply: String,
    val recommendedCoffeeIds: List<Long>
)