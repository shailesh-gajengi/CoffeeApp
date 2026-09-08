package com.example.coffeeapp.mapper

import android.content.Context
import android.util.Log
import com.example.coffeeapp.dto.CoffeeResponse
import com.example.coffeeapp.model.Product

fun CoffeeResponse.toProduct(context: Context): Product {

    val drawableName = imageUrl.substringBeforeLast(".")

    val imageRes = context.resources.getIdentifier(
        drawableName,
        "drawable",
        context.packageName
    )

    Log.d("CoffeeAPI", "Image = $drawableName  Res = $imageRes")

    return Product(
        id = id.toInt(),
        name = name,
        description = description,
        price = price,
        imageRes = imageRes,
        category = category
    )
}