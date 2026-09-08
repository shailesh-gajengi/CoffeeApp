package com.example.coffeeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeCategories(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {

    val categories = listOf(
        "All Coffees",
        "Americano",
        "Cappuccino",
        "Cold Brew",
        "Cold Coffee",
        "Dessert Coffee",
        "Espresso",
        "Frappe",
        "Latte",
        "Milk Coffee",
        "Mocha",
        "Special",
        "Specialty"
    )

    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(categories) { category ->

            CategoryChip(
                text = category,
                isSelected = category == selectedCategory,
                onSelected = {
                    onCategorySelected(category)
                }
            )
        }
    }
}