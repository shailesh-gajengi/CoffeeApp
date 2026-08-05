package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.components.CartItem
import com.example.coffeeapp.ui.components.CartScreenTopBar
import com.example.coffeeapp.ui.components.MyNavBar
import com.example.coffeeapp.ui.components.PaymentCard
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.viewmodel.CartViewModel

@Composable
 fun CartScreen(navController: NavController) {
    val context = LocalContext.current

    val cartViewModel: CartViewModel = viewModel()

    val cartItems by cartViewModel.cartItems.collectAsState()
    var deliveryFee by remember { mutableStateOf(0.0) }
    val amount = cartItems.sumOf {
        it.price * it.quantity
    }
    var total  = amount + deliveryFee

    LaunchedEffect(Unit) {
        cartViewModel.getCart(
            userId = 1,
            context = context
        )
    }
    Scaffold(
        topBar = { CartScreenTopBar(navController) },
        bottomBar = { MyNavBar( navController,"Cart") }
    ){ innerPadding->

        LazyColumn(
            modifier = Modifier.padding(16.dp).padding(innerPadding)
        ) {

            item {
                Row() {
                    Text(
                        text = "Deliver",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightBrown
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            items(cartItems) { cart ->

                CartItem(
                    cartProduct = cart,
                    cartViewModel = cartViewModel,
                    context = context
                )

            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Payment Summary",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Price", fontSize = 18.sp)
                    Text(text = "$ $amount", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Delivery Fee", fontSize = 18.sp)
                    Text(text = "$ $deliveryFee", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                PaymentCard(total, deliveryFee)

            }

        }
        }

    }
