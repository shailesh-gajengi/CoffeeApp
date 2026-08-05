package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.components.FavouriteItemCart
import com.example.coffeeapp.ui.components.FavouriteScreenTopBar
import com.example.coffeeapp.ui.components.MyNavBar

@Composable
 fun FavouriteScreen( navController: NavController) {
    var FavProducts by remember {
        mutableStateOf(
            listOf(
                Product(1, "Espresso", "Strong and rich", 3.80, R.drawable.coffee_2),
                Product(6, "Flat White", "Velvety smooth", 4.40, R.drawable.coffee_6),
                Product(7, "Iced Mocha", "Refreshing and rich", 4.70, R.drawable.coffee_4)
            )
        )
    }
    Scaffold(
        topBar = { FavouriteScreenTopBar() },
        bottomBar = { MyNavBar(navController,"Favourite") }
    ) {
        innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp).padding(innerPadding)
        ) {
            item{
                FavProducts.forEach { product ->
                    FavouriteItemCart(product,onRemove = {FavProducts = FavProducts - product   })

                }
            }

        }

    }
    }

