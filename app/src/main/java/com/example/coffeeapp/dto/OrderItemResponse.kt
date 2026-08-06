package com.example.coffeeapp.dto

data class OrderItemResponse(
    val coffeeId: Long,
    val coffeeName: String,
    val price: Double,
    val quantity: Int
)