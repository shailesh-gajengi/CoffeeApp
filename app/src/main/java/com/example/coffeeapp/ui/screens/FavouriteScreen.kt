package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.ui.components.FavouriteItemCart
import com.example.coffeeapp.ui.components.FavouriteScreenTopBar
import com.example.coffeeapp.ui.components.MyNavBar
import com.example.coffeeapp.viewmodel.FavouriteViewModel

@Composable
fun FavouriteScreen(navController: NavController) {

    val context = LocalContext.current

    val favouriteViewModel: FavouriteViewModel = viewModel()

    val favourites by favouriteViewModel.favourites.collectAsState()

    LaunchedEffect(Unit) {
        favouriteViewModel.getFavourite(
            userId = 1,
            context = context
        )
    }

    Scaffold(
        topBar = { FavouriteScreenTopBar() },
        bottomBar = { MyNavBar(navController, "Favourite") }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            items(favourites) { favourite ->

                FavouriteItemCart(
                    favourite = favourite,
                    favouriteViewModel = favouriteViewModel,
                    context = context
                )

            }

        }

    }
}