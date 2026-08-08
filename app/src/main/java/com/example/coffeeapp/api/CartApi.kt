package com.example.coffeeapp.api

import CartRequest
import com.example.coffeeapp.dto.CartResponse
import retrofit2.Response
import retrofit2.http.*

interface CartApi {

    @POST("cart")
    suspend fun addToCart(
        @Body request: CartRequest
    ): Response<CartResponse>

    @GET("cart/user/{userId}")
    suspend fun getCart(
        @Path("userId") userId: String
    ): Response<List<CartResponse>>

    @PUT("cart/{id}")
    suspend fun updateQuantity(
        @Path("id") id: Long,
        @Query("quantity") quantity: Int
    ): Response<CartResponse>

    @DELETE("cart/{id}")
    suspend fun removeFromCart(
        @Path("id") id: Long
    ): Response<String>
}