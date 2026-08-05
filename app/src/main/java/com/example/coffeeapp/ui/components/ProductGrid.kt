package com.example.coffeeapp.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.FavouriteViewModel


@Composable
fun ProductGrid(
    navController: NavController,
    modifier: Modifier,
    favouriteViewModel: FavouriteViewModel,
    context: Context,
    products: List<Product>,
    cartViewModel: CartViewModel,
    topContent: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        item(){
            topContent()
        }
        items(items = products.chunked(2)) { rowtems ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProductCard(
                    navController = navController,
                    product = rowtems[0],
                    cartViewModel = cartViewModel,
                    modifier = Modifier.weight(1f),
                    favouriteViewModel = favouriteViewModel,
                    context = context
                )
                if(rowtems.size==2){
                    ProductCard(
                        navController = navController,
                        product = rowtems[1],
                        cartViewModel = cartViewModel,
                        modifier = Modifier.weight(1f),
                        favouriteViewModel = favouriteViewModel,
                        context = context
                    )
                }else{
                    Spacer(modifier = Modifier.weight(1f))
                }

            }

        }
    }
}
