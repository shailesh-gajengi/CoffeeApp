package com.example.coffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.api.OrderApi
import com.example.coffeeapp.dto.OrderRequest
import com.example.coffeeapp.mapper.toOrderProduct
import com.example.coffeeapp.model.OrderProduct
import com.example.coffeeapp.network.RetrofitInstance
import com.example.coffeeapp.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val api =
        RetrofitInstance.retrofit.create(OrderApi::class.java)

    private val repository =
        OrderRepository(api)

    private val _orders =
        MutableStateFlow<List<OrderProduct>>(emptyList())

    val orders: StateFlow<List<OrderProduct>>
        get() = _orders

    fun placeOrder(request: OrderRequest) {

        viewModelScope.launch {

            try {

                val response = repository.placeOrder(request)

                if (response.isSuccessful) {
                    getOrders(request.userId)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getOrders(userId: Long) {

        viewModelScope.launch {

            try {

                val response = repository.getOrders(userId)

                if (response.isSuccessful) {

                    _orders.value =
                        response.body()
                            ?.map { it.toOrderProduct() }
                            ?: emptyList()

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}