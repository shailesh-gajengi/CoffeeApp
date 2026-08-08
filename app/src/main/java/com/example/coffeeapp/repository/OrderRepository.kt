package com.example.coffeeapp.repository

import com.example.coffeeapp.api.OrderApi
import com.example.coffeeapp.dto.OrderRequest

class OrderRepository(
    private val api: OrderApi
) {

    suspend fun placeOrder(request: OrderRequest) =
        api.placeOrder(request)

    suspend fun getOrders(userId: String) =
        api.getOrders(userId)

    suspend fun getOrderById(id: Long) =
        api.getOrderById(id)
}