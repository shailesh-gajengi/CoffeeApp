package com.example.coffeeapp.model

data class OrderProduct(
    val orderId: Long,
    val totalAmount: Double,
    val paymentMethod: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val coffeeName: String,
    val price: Double,
    val quantity: Int
)