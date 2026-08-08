package com.example.coffeeapp.api

import com.example.coffeeapp.dto.OrderRequest
import com.example.coffeeapp.dto.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {

    @POST("orders")
    suspend fun placeOrder(
        @Body request: OrderRequest
    ): Response<OrderResponse>

    @GET("orders/user/{userId}")
    suspend fun getOrders(
        @Path("userId") userId: String
    ): Response<List<OrderResponse>>

    @GET("orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: Long
    ): Response<OrderResponse>
}