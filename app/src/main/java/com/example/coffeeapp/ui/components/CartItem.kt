package com.example.coffeeapp.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coffeeapp.model.CartProduct
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.ui.theme.LightGray
import com.example.coffeeapp.viewmodel.CartViewModel

@Composable
fun CartItem(
    cartProduct: CartProduct,
    cartViewModel: CartViewModel,
    context: Context
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(cartProduct.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {

                Text(
                    text = cartProduct.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = "$ ${cartProduct.price}",
                    color = Color.Gray
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {

                        if (cartProduct.quantity > 1) {

                            cartViewModel.updateQuantity(
                                id = cartProduct.id,
                                quantity = cartProduct.quantity - 1,
                                context = context,
                                userId = cartProduct.userId
                            )

                        } else {

                            cartViewModel.removeFromCart(
                                id = cartProduct.id,
                                context = context,
                                userId = cartProduct.userId
                            )

                        }

                    },
                    modifier = Modifier
                        .background(
                            LightBrown.copy(0.2f),
                            CircleShape
                        )
                        .size(24.dp)
                ) {

                    Icon(
                        Icons.Default.Remove,
                        contentDescription = null
                    )

                }

                Text(
                    text = cartProduct.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(
                    onClick = {

                        cartViewModel.updateQuantity(
                            id = cartProduct.id,
                            quantity = cartProduct.quantity + 1,
                            context = context,
                            userId = cartProduct.userId
                        )

                    },
                    modifier = Modifier
                        .background(
                            LightBrown.copy(0.2f),
                            CircleShape
                        )
                        .size(24.dp)
                ) {

                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )

                }

            }

        }

    }
}