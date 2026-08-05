package com.example.coffeeapp.viewmodel

import CartRequest
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.api.CartApi
import com.example.coffeeapp.mapper.toCartProduct
import com.example.coffeeapp.model.CartProduct
import com.example.coffeeapp.network.RetrofitInstance
import com.example.coffeeapp.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val api =
        RetrofitInstance.retrofit.create(CartApi::class.java)

    private val repository =
        CartRepository(api)

    private val _cartItems =
        MutableStateFlow<List<CartProduct>>(emptyList())

    val cartItems: StateFlow<List<CartProduct>>
        get() = _cartItems

    fun addToCart(
        userId: Long,
        coffeeId: Long,
        quantity: Int = 1
    ) {

        viewModelScope.launch {

            try {

                val response = repository.addToCart(
                    CartRequest(userId, coffeeId, quantity)
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getCart(
        userId: Long,
        context: Context
    ) {

        viewModelScope.launch {

            try {

                val response = repository.getCart(userId)

                if (response.isSuccessful) {

                    _cartItems.value =
                        response.body()
                            ?.map { it.toCartProduct(context) }
                            ?: emptyList()

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun updateQuantity(
        id: Long,
        quantity: Int,
        context: Context
    ) {

        viewModelScope.launch {

            try {

                repository.updateQuantity(id, quantity)

                getCart(1, context)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun removeFromCart(
        id: Long,
        context: Context
    ) {

        viewModelScope.launch {

            try {

                repository.removeFromCart(id)

                getCart(1, context)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}