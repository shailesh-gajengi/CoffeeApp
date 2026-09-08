package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeeapp.ui.components.HomeCategories
import com.example.coffeeapp.ui.components.MyNavBar
import com.example.coffeeapp.ui.components.ProductGrid
import com.example.coffeeapp.R
import com.example.coffeeapp.ui.components.SearchBar
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.FavouriteViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val coffeeViewModel: CoffeeViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val favouriteViewModel: FavouriteViewModel = viewModel()
    val products by coffeeViewModel.coffeeList.collectAsState()
    var selectedCategory by remember {
        mutableStateOf("All Coffees")
    }
    var searchQuery by remember {
        mutableStateOf("")
    }
    val filteredProducts = products.filter { product ->

        val matchesCategory =
            selectedCategory == "All Coffees" ||
                    product.category.equals(
                        selectedCategory,
                        ignoreCase = true
                    )

        val matchesSearch =
            searchQuery.isBlank() ||
                    product.name.contains(
                        searchQuery,
                        ignoreCase = true
                    ) ||
                    product.description.contains(
                        searchQuery,
                        ignoreCase = true
                    )

        matchesCategory && matchesSearch
    }


    val favourites by favouriteViewModel.favourites.collectAsState()
    LaunchedEffect(Unit) {
        coffeeViewModel.getAllCoffee(context)
    }
    LaunchedEffect(Unit) {
        userId?.let {
            favouriteViewModel.getFavourite(
                userId = it,
                context = context
            )
        }
    }
    Scaffold(
        bottomBar = { MyNavBar(navController,"Home") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.ChatScreen)
                },
                containerColor = Color(0xFF6F4E37)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "AI Chat",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        // This Box allows us to layer the black background BEHIND the scrolling list
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9)) // The light background for the bottom
        ) {
            // 1. THE BLACK BACKGROUND (Fixed at the top)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp) // Covers the top area
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF303030),
                                Color(0xFF1F1F1F),
                                Color(0xFF121212)
                            )
                        )
                    )
            )

            // 2. THE SCROLLABLE CONTENT (ProductGrid is your Lazy component)
            ProductGrid(
                navController = navController,
                modifier = Modifier.padding(innerPadding),

                favouriteViewModel = favouriteViewModel,
                favourites = favourites,
                context = context,
                products = filteredProducts,
                cartViewModel = cartViewModel,

                topContent = {
                    // This Column stays inside the Lazy list so it scrolls up with the products
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text("Location", color = Color.LightGray, fontSize = 12.sp)

                        Row {
                            Text("Janatha Rd, Palarivattom", color = Color.White, fontSize = 14.sp)
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        SearchBar(
                            query = searchQuery,
                            onQueryChange = {
                                searchQuery = it
                            }
                        )

                        Spacer(Modifier.height(24.dp))

                        // PROMO BANNER
                        Image(
                            painter = painterResource(R.drawable.banner_1),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp)), // Rounded like your image
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(20.dp))

                        HomeCategories(
                            selectedCategory = selectedCategory,
                            onCategorySelected = {
                                selectedCategory = it
                            }
                        )

                    }
                }
            )
        }
    }
}