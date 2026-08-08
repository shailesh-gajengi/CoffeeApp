package com.example.coffeeapp.api

import CartRequest
import com.example.coffeeapp.dto.CartResponse
import com.example.coffeeapp.dto.CoffeeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApi {

    @GET("coffee")
    suspend fun getAllCoffee(): Response<List<CoffeeResponse>>

    @GET("coffee/{id}")
    suspend fun getCoffeeById(
        @Path("id") id: Long
    ): Response<CoffeeResponse>

    @POST("cart")
    suspend fun addToCart(
        @Body request: CartRequest
    ): Response<CartResponse>

    @GET("cart/user/{userId}")
    suspend fun getCart(
        @Path("userId") userId: String
    ): Response<List<CartResponse>>

}