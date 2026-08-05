package com.example.coffeeapp.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeeapp.R
import com.example.coffeeapp.ui.theme.LightBrown

@Composable
fun PaymentCard(total: Double, deliveryFee: Double) {
    // 1. These states MUST be inside the component
    var expanded by remember { mutableStateOf(false) }
    var SelectedMode by remember { mutableStateOf("Online") }
    val paymentModes = listOf("Online", "Cash on Delivery")

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(if(SelectedMode == "Online")R.drawable.mobile_banking else R.drawable.wallet),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = LightBrown
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = SelectedMode, // Shows what you picked
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = "$ $total",
                            style = MaterialTheme.typography.bodyLarge,
                            color = LightBrown
                        )
                    }
                }

                // --- THIS BOX IS THE ANCHOR ---
                Box {
                    Icon(
                        painter = painterResource(R.drawable.regular_outline_arrow_down),
                        contentDescription = "Open Menu",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { expanded = true } // 2. Toggles the menu open
                    )

                    // 3. DropdownMenu MUST be inside the Box with the Icon
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        paymentModes.forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(text = mode) },
                                onClick = {
                                    SelectedMode = mode // Updates the selection
                                    expanded = false    // Closes the menu
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(
                                            if (mode == "Online") R.drawable.mobile_banking else R.drawable.wallet

                                        ),
                                        contentDescription = null,
                                        tint = LightBrown,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                modifier = Modifier.background(
                                    color = if (mode == SelectedMode) LightBrown.copy(0.15f) else Color.Transparent
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Order logic */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBrown),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Place Order", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}