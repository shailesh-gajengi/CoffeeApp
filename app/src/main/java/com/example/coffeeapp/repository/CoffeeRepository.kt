package com.example.coffeeapp.repository

import com.example.coffeeapp.api.CoffeeApi
import com.example.coffeeapp.dto.CoffeeResponse

class CoffeeRepository(
    private val api: CoffeeApi
) {

    suspend fun getAllCoffee() =
        api.getAllCoffee()

    suspend fun getCoffeeById(id: Long) =
        api.getCoffeeById(id)

}