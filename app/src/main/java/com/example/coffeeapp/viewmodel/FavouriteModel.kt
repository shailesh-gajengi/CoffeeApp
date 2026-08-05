package com.example.coffeeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.api.FavouriteApi
import com.example.coffeeapp.dto.FavouriteRequest
import com.example.coffeeapp.mapper.toFavouriteProduct
import com.example.coffeeapp.model.FavouriteProduct
import com.example.coffeeapp.network.RetrofitInstance
import com.example.coffeeapp.repository.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    private val api =
        RetrofitInstance.retrofit.create(FavouriteApi::class.java)

    private val repository =
        FavouriteRepository(api)

    private val _favourites =
        MutableStateFlow<List<FavouriteProduct>>(emptyList())

    val favourites: StateFlow<List<FavouriteProduct>>
        get() = _favourites

    fun addFavourite(
        userId: Long,
        coffeeId: Long,
        context: Context
    ) {

        viewModelScope.launch {

            try {

                val response = repository.addFavourite(
                    FavouriteRequest(userId, coffeeId)
                )

                if (response.isSuccessful) {

                    response.body()?.let {

                        _favourites.value =
                            _favourites.value + it.toFavouriteProduct(context)

                    }

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getFavourite(
        userId: Long,
        context: Context
    ) {

        viewModelScope.launch {

            try {

                val response = repository.getFavourite(userId)

                if (response.isSuccessful) {

                    _favourites.value =
                        response.body()
                            ?.map { it.toFavouriteProduct(context) }
                            ?: emptyList()

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun removeFavourite(
        id: Long,
        userId: Long,
        context: Context
    ) {
        viewModelScope.launch {

            // Update UI immediately
            _favourites.value = _favourites.value.filter { it.id != id }

            try {
                repository.removeFavourite(id)
            } catch (e: Exception) {
                e.printStackTrace()
                // Optional: reload if request fails
                getFavourite(userId, context)
            }
        }
    }
}