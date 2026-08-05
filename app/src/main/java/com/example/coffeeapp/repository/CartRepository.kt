package com.example.coffeeapp.repository

import CartRequest
import com.example.coffeeapp.api.CartApi

class CartRepository(
    private val api: CartApi
) {

    suspend fun addToCart(request: CartRequest) =
        api.addToCart(request)

    suspend fun getCart(userId: Long) =
        api.getCart(userId)

    suspend fun updateQuantity(id: Long, quantity: Int) =
        api.updateQuantity(id, quantity)

    suspend fun removeFromCart(id: Long) =
        api.removeFromCart(id)
}