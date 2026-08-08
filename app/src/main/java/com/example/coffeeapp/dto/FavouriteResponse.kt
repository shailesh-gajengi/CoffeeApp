package com.example.coffeeapp.dto

data class FavouriteResponse(
    val id: Long,
    val userId: String,
    val coffeeId: Long,
    val coffeeName: String,
    val price: Double,
    val imageUrl: String
)