package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.ui.components.DetailScreenBottomBar
import com.example.coffeeapp.ui.components.DetailScreenTopBar
import com.example.coffeeapp.ui.components.ProductDetailContent
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.CoffeeViewModel

@Composable
fun DetailScreen(productId: Int, navController: NavController) {

    val context = LocalContext.current
    val viewModel: CoffeeViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val product by viewModel.selectedCoffee.collectAsState()

    LaunchedEffect(productId) {
        viewModel.getCoffeeById(productId.toLong(), context)
    }

    if (product == null) {
        Text("Loading...")
        return
    }
    Scaffold(
        topBar = { DetailScreenTopBar(navController) },
        bottomBar = {
            DetailScreenBottomBar(
                navController = navController,
                product = product!!,
                cartViewModel = cartViewModel
            )
        }

    ) {innerPadding->

        LazyColumn{
            item{
                ProductDetailContent(
                    product!!,
                    innerPadding
                )
            }

        }

    }
}