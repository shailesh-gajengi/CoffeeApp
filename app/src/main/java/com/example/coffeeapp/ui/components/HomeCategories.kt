package com.example.coffeeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf


@Preview
@Composable
fun HomeCategories() {
    val categories = listOf("All Coffees","Macchiato","Latte","Americano","Snacks","Desserts")
    val selectedCategory by remember { mutableStateOf(categories.first()) }
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories){Category->
            CategoryChip(
                text = Category,
                isSelected = Category == selectedCategory,
                onSelected = { selectedCategory == Category }
            )
        }
    }
}