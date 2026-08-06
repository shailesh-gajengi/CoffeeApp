package com.example.coffeeapp.dto

data class OrderItemRequest(
    val coffeeId: Long,
    val quantity: Int
)