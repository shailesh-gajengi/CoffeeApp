package com.example.coffeeapp.dto

data class OrderRequest(
    val userId: String,
    val paymentMethod: String,
    val items: List<OrderItemRequest>
)