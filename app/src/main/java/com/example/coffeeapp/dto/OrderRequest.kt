package com.example.coffeeapp.dto

data class OrderRequest(
    val userId: Long,
    val paymentMethod: String,
    val items: List<OrderItemRequest>
)