package com.example.coffeeapp.mapper

import com.example.coffeeapp.dto.OrderResponse
import com.example.coffeeapp.model.OrderItem
import com.example.coffeeapp.model.OrderProduct

fun OrderResponse.toOrderProduct(): OrderProduct {

    return OrderProduct(
        orderId = orderId,
        totalAmount = totalAmount,
        paymentMethod = paymentMethod,
        items = items.map {
            OrderItem(
                coffeeName = it.coffeeName,
                price = it.price,
                quantity = it.quantity
            )
        }
    )
}