    package com.example.coffeeapp.repository

    import com.example.coffeeapp.api.FavouriteApi
    import com.example.coffeeapp.dto.FavouriteRequest

    class FavouriteRepository(
        private val api: FavouriteApi
    ) {

        suspend fun addFavourite(request: FavouriteRequest) =
            api.addFavourite(request)

        suspend fun getFavourite(userId: String) =
            api.getFavourite(userId)

        suspend fun removeFavourite(id: Long) =
            api.removeFavourite(id)
    }