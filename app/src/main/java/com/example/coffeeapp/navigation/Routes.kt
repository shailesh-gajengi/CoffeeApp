    package com.example.coffeeapp.navigation

    import kotlinx.serialization.Serializable

    sealed class Routes {

        @Serializable
        object WelcomeScreen : Routes()
        @Serializable
        object LoginScreen : Routes()

        @Serializable
        object RegisterScreen : Routes()
        @Serializable
        object HomeScreen : Routes()

        @Serializable
        object OrderScreen : Routes()
        @Serializable
        data class DetailScreen(val productId: Int) : Routes()

        @Serializable
        object CartScreen : Routes()
        @Serializable
        object FavouriteScreen : Routes()
        @Serializable
        object ProfileScreen : Routes()
    }
