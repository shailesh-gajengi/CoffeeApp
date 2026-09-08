package com.example.coffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coffeeapp.ui.screens.CartScreen
import com.example.coffeeapp.ui.screens.ChatScreen
import com.example.coffeeapp.ui.screens.DetailScreen
import com.example.coffeeapp.ui.screens.FavouriteScreen
import com.example.coffeeapp.ui.screens.HomeScreen
import com.example.coffeeapp.ui.screens.LoginScreen
import com.example.coffeeapp.ui.screens.OrderScreen
import com.example.coffeeapp.ui.screens.ProfileScreen
import com.example.coffeeapp.ui.screens.RegisterScreen
import com.example.coffeeapp.ui.screens.SettingsScreen
import com.example.coffeeapp.ui.screens.SplashScreen
import com.example.coffeeapp.ui.screens.WelcomeScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen
    ) {

        // ---------------------------------------------------------
        // SPLASH
        // ---------------------------------------------------------

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }

        // ---------------------------------------------------------
        // LOGIN
        // ---------------------------------------------------------

        composable<Routes.LoginScreen> {
            LoginScreen(navController)
        }

        // ---------------------------------------------------------
        // REGISTER
        // ---------------------------------------------------------

        composable<Routes.RegisterScreen> {
            RegisterScreen(navController)
        }

        // ---------------------------------------------------------
        // WELCOME
        // ---------------------------------------------------------

        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        // ---------------------------------------------------------
        // HOME
        // ---------------------------------------------------------

        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }

        // ---------------------------------------------------------
        // PRODUCT DETAIL
        // ---------------------------------------------------------

        composable<Routes.DetailScreen> { backStackEntry ->

            val args =
                backStackEntry.toRoute<Routes.DetailScreen>()

            DetailScreen(
                args.productId,
                navController
            )
        }

        // ---------------------------------------------------------
        // CART
        // ---------------------------------------------------------

        composable<Routes.CartScreen> {
            CartScreen(navController)
        }

        // ---------------------------------------------------------
        // FAVOURITES
        // ---------------------------------------------------------

        composable<Routes.FavouriteScreen> {
            FavouriteScreen(navController)
        }

        // ---------------------------------------------------------
        // ORDER
        // ---------------------------------------------------------

        composable<Routes.OrderScreen> {
            OrderScreen(navController)
        }

        // ---------------------------------------------------------
        // MY ORDERS
        // ---------------------------------------------------------

        composable<Routes.OrdersScreen> {
            OrderScreen(navController)
        }

        // ---------------------------------------------------------
        // PROFILE
        // ---------------------------------------------------------

        composable<Routes.ProfileScreen> {
            ProfileScreen(navController)
        }

        // ---------------------------------------------------------
        // SETTINGS
        // ---------------------------------------------------------

        composable<Routes.SettingsScreen> {
            SettingsScreen(navController)
        }

        // ---------------------------------------------------------
        // AI CHAT
        // ---------------------------------------------------------

        composable<Routes.ChatScreen> {
            ChatScreen(navController)
        }
    }
}