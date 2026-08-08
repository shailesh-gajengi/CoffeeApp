package com.example.coffeeapp.dto

data class OrderResponse(
    val orderId: Long,
    val userId: String,
    val totalAmount: Double,
    val paymentMethod: String,
    val items: List<OrderItemResponse>
)