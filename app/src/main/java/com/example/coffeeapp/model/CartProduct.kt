package com.example.coffeeapp.model

data class CartProduct(
    val id: Long,
    val coffeeId: Long,
    val userId: String,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val quantity: Int
)