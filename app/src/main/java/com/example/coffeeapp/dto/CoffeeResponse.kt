package com.example.coffeeapp.dto

data class CoffeeResponse(

    val id: Long,

    val name: String,

    val description: String,

    val price: Double,

    val imageUrl: String,

    val category: String

)