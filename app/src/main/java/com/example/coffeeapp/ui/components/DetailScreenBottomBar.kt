package com.example.coffeeapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.ui.theme.IvoryWhite
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DetailScreenBottomBar(
    navController: NavController,
    product: Product,
    cartViewModel: CartViewModel
) {

    var showCartDialogue by remember {
        mutableStateOf(false)
    }

    val userId = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid

    BottomAppBar(
        containerColor = Color.Transparent
    ) {

        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Column {

                Text(
                    text = "Price",
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "$ ${product.price}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.width(40.dp)
            )

            Button(
                onClick = {

                    userId?.let { uid ->

                        cartViewModel.addToCart(
                            userId = uid,
                            coffeeId = product.id.toLong(),
                            quantity = 1
                        )

                        showCartDialogue = true
                    }

                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown,
                    contentColor = IvoryWhite
                )
            ) {

                Text(
                    text = "Add to Cart",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        AppMessageDailogue(
            show = showCartDialogue,
            title = "Added to Cart",
            message = "Item added to cart",
            onDismiss = {
                showCartDialogue = false
            }
        )
    }
}