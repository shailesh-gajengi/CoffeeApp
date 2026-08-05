package com.example.coffeeapp.mapper

import android.content.Context
import com.example.coffeeapp.R
import com.example.coffeeapp.dto.FavouriteResponse
import com.example.coffeeapp.model.FavouriteProduct

fun FavouriteResponse.toFavouriteProduct(
    context: Context
): FavouriteProduct {

    val res = context.resources.getIdentifier(
        imageUrl.substringBefore("."),
        "drawable",
        context.packageName
    )

    return FavouriteProduct(
        id = id,
        coffeeId = coffeeId,
        name = coffeeName,
        price = price,
        imageRes = if (res != 0) res else R.drawable.default_bean
    )
}