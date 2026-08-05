package com.example.coffeeapp.mapper

import android.content.Context
import com.example.coffeeapp.dto.CartResponse
import com.example.coffeeapp.model.CartProduct

fun CartResponse.toCartProduct(context: Context): CartProduct {

    val res = context.resources.getIdentifier(
        imageUrl.substringBefore("."),
        "drawable",
        context.packageName
    )

    return CartProduct(
        id = id,
        coffeeId = coffeeId,
        name = coffeeName,
        price = price,
        imageRes = if (res != 0) res else com.example.coffeeapp.R.drawable.default_bean,
        quantity = quantity
    )
}