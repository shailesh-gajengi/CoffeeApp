package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.dto.OrderItemRequest
import com.example.coffeeapp.dto.OrderRequest
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.components.CartItem
import com.example.coffeeapp.ui.components.CartScreenTopBar
import com.example.coffeeapp.ui.components.MyNavBar
import com.example.coffeeapp.ui.components.PaymentCard
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CartScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val userId = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid

    val cartViewModel: CartViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()

    val cartItems by cartViewModel.cartItems.collectAsState()

    var deliveryFee by remember {
        mutableStateOf(0.0)
    }

    val amount = cartItems.sumOf {
        it.price * it.quantity
    }

    val total = amount + deliveryFee


    // -------------------------
    // LOAD CART
    // -------------------------

    LaunchedEffect(userId) {

        userId?.let {

            cartViewModel.getCart(
                userId = it,
                context = context
            )
        }
    }


    // -------------------------
    // SCREEN
    // -------------------------

    Scaffold(

        topBar = {
            CartScreenTopBar(navController)
        },

        bottomBar = {
            MyNavBar(
                navController,
                "Cart"
            )
        }

    ) { innerPadding ->


        if (cartItems.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

        } else {

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)

            ) {

                // -------------------------
                // DELIVERY TITLE
                // -------------------------

                item {

                    Text(
                        text = "Deliver",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightBrown
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }


                // -------------------------
                // CART ITEMS
                // -------------------------

                items(cartItems) { cart ->

                    CartItem(
                        cartProduct = cart,
                        cartViewModel = cartViewModel,
                        context = context
                    )
                }


                // -------------------------
                // PAYMENT SUMMARY
                // -------------------------

                item {

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = "Payment Summary",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    // PRICE

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Price",
                            fontSize = 18.sp
                        )

                        Text(
                            text = "$ $amount",
                            fontSize = 18.sp
                        )
                    }


                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )


                    // DELIVERY FEE

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Delivery Fee",
                            fontSize = 18.sp
                        )

                        Text(
                            text = "$ $deliveryFee",
                            fontSize = 18.sp
                        )
                    }


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    // -------------------------
                    // PAYMENT CARD
                    // -------------------------

                    PaymentCard(

                        total = total,

                        deliveryFee = deliveryFee

                    ) { paymentMethod ->


                        // Make sure user is logged in

                        if (userId == null) {
                            return@PaymentCard
                        }


                        // -------------------------
                        // CREATE ORDER REQUEST
                        // -------------------------

                        val request = OrderRequest(

                            userId = userId,

                            paymentMethod = paymentMethod,

                            items = cartItems.map {

                                OrderItemRequest(

                                    coffeeId = it.coffeeId,

                                    quantity = it.quantity
                                )
                            }
                        )


                        // -------------------------
                        // PLACE ORDER
                        // -------------------------

                        orderViewModel.placeOrder(request)


                        // -------------------------
                        // CLEAR CART
                        // -------------------------
                        //
                        // IMPORTANT:
                        //
                        // Your current OrderViewModel
                        // doesn't expose order success.
                        //
                        // Therefore this is still
                        // asynchronous.
                        //
                        // We'll improve this in the
                        // next step.
                        //

                        cartItems.forEach {

                            cartViewModel.removeFromCart(

                                id = it.id,

                                context = context,

                                userId = userId
                            )
                        }


                        // -------------------------
                        // GO TO ORDERS
                        // -------------------------

                        navController.navigate(
                            Routes.OrdersScreen
                        )
                    }
                }
            }
        }
    }
}