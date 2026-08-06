package com.example.coffeeapp.dto

data class OrderResponse(
    val orderId: Long,
    val userId: Long,
    val totalAmount: Double,
    val paymentMethod: String,
    val items: List<OrderItemResponse>
)