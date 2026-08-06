package com.example.coffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coffeeapp.ui.screens.CartScreen
import com.example.coffeeapp.ui.screens.DetailScreen
import com.example.coffeeapp.ui.screens.FavouriteScreen
import com.example.coffeeapp.ui.screens.HomeScreen
import com.example.coffeeapp.ui.screens.LoginScreen
import com.example.coffeeapp.ui.screens.OrderScreen
import com.example.coffeeapp.ui.screens.ProfileScreen
import com.example.coffeeapp.ui.screens.RegisterScreen
import com.example.coffeeapp.ui.screens.WelcomeScreen

@Composable
fun NavGraph()
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.WelcomeScreen
    ){
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }
        composable<Routes.OrderScreen> {
            OrderScreen(navController)
        }
        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }
        composable<Routes.LoginScreen> {
            LoginScreen(navController)
        }

        composable<Routes.RegisterScreen> {
            RegisterScreen(navController)
        }
        composable<Routes.DetailScreen> {backStackEntry ->
            val args = backStackEntry.toRoute<Routes.DetailScreen>()
            DetailScreen(args.productId, navController)
        }
        composable<Routes.CartScreen> {
            CartScreen(navController)
        }
        composable<Routes.FavouriteScreen> {
            FavouriteScreen(navController)
        }
        composable<Routes.ProfileScreen> {
            ProfileScreen(navController)
        }
    }
}

