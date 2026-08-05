package com.example.coffeeapp.api

import com.example.coffeeapp.dto.FavouriteRequest
import com.example.coffeeapp.dto.FavouriteResponse
import retrofit2.Response
import retrofit2.http.*

interface FavouriteApi {

    @POST("favourite")
    suspend fun addFavourite(
        @Body request: FavouriteRequest
    ): Response<FavouriteResponse>

    @GET("favourite/user/{userId}")
    suspend fun getFavourite(
        @Path("userId") userId: Long
    ): Response<List<FavouriteResponse>>

    @DELETE("favourite/{id}")
    suspend fun removeFavourite(
        @Path("id") id: Long
    ): Response<String>
}