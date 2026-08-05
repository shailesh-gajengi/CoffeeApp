package com.example.coffeeapp.model

data class FavouriteProduct(
    val id: Long,
    val coffeeId: Long,
    val name: String,
    val price: Double,
    val imageRes: Int
)