package com.example.coffeeapp.dto

data class FavouriteResponse(
    val id: Long,
    val userId: Long,
    val coffeeId: Long,
    val coffeeName: String,
    val price: Double,
    val imageUrl: String
)