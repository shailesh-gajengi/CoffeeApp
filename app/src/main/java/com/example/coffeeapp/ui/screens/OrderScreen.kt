package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.ui.components.MyNavBar
import com.example.coffeeapp.ui.components.OrderCard
import com.example.coffeeapp.ui.components.OrderScreenTopBar
import com.example.coffeeapp.viewmodel.OrderViewModel

@Composable
fun OrderScreen(
    navController: NavController
) {

    val orderViewModel: OrderViewModel = viewModel()

    val orders by orderViewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        orderViewModel.getOrders(1)
    }

    Scaffold(
        topBar = {
            OrderScreenTopBar(navController)
        },
        bottomBar = {
            MyNavBar(
                navController,
                "Profile"
            )
        }
    ){ innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            items(orders) { order ->

                OrderCard(order)

            }

        }

    }

}