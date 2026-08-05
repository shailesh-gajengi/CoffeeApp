package com.example.coffeeapp.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.theme.IvoryWhite
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.ui.theme.LightGray
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.FavouriteViewModel

@Composable
fun ProductCard(navController: NavController,
                product: Product,
                modifier: Modifier = Modifier,
                cartViewModel: CartViewModel,
                favouriteViewModel: FavouriteViewModel,
                context: Context,
) {
    Card(
        // 1. Remove .width(250.dp). Let the Grid's .weight(1f) handle the width.
        // 2. Padding is applied here to give the shadow room to breathe.
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.DetailScreen(product.id)) }
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            // 3. Elevation needs a solid color to be visible.
            // Semi-transparent colors like .copy(0.3f) hide the shadow.
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // Reduced height from 250dp for better grid proportions
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = "com.example.coffeeapp.model.Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(16.dp))
                )

                Box(
                    modifier = Modifier.align(Alignment.TopEnd).
                    background(
                        color = LightGray.copy(0.8f),
                        shape = RoundedCornerShape(12.dp)
                    ).padding(0.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.regular_outline_heart),
                        contentDescription = "Favourite",
                        tint = LightBrown,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                                favouriteViewModel.addFavourite(
                                    userId = 1,
                                    coffeeId = product.id.toLong(),
                                    context = context
                                )

                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Name
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = product.name,
                style = typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Description
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = product.description,
                style = typography.bodySmall.copy(
                    color = Color.Gray,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price and Add Button Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ ${product.price}",
                    style = typography.titleMedium.copy(
                        color = LightBrown,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                IconButton(
                    onClick = {

                        cartViewModel.addToCart(
                            userId = 1,
                            coffeeId = product.id.toLong(),
                            quantity = 1
                        )

                    },
                    modifier = Modifier
                        .size(36.dp) // Fixed size for the button
                        .background(
                            color = LightBrown,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to Cart",
                        tint = IvoryWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}