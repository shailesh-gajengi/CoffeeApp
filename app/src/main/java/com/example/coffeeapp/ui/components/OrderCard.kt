package com.example.coffeeapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeeapp.model.OrderProduct
import com.example.coffeeapp.ui.theme.LightGray

@Composable
fun OrderCard(
    order: OrderProduct
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Order #${order.orderId}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Payment : ${order.paymentMethod}"
            )

            Text(
                text = "Total : $ ${order.totalAmount}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            order.items.forEach {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = it.coffeeName
                    )

                    Text(
                        text = "x${it.quantity}"
                    )

                    Text(
                        text = "$ ${it.price}"
                    )

                }

                Spacer(modifier = Modifier.height(6.dp))

            }

        }

    }

}