package com.example.coffeeapp.viewmodel
import android.util.Log
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.api.CoffeeApi
import com.example.coffeeapp.mapper.toProduct
import com.example.coffeeapp.model.Product
import com.example.coffeeapp.network.RetrofitInstance
import com.example.coffeeapp.repository.CoffeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoffeeViewModel : ViewModel() {

    private val api =
        RetrofitInstance.retrofit.create(CoffeeApi::class.java)

    private val repository =
        CoffeeRepository(api)

    private val _coffeeList =
        MutableStateFlow<List<Product>>(emptyList())

    val coffeeList: StateFlow<List<Product>>
        get() = _coffeeList



    private val _selectedCoffee = MutableStateFlow<Product?>(null)
    val selectedCoffee: StateFlow<Product?> = _selectedCoffee

    fun getCoffeeById(id: Long, context: Context) {
        viewModelScope.launch {
            try {
                val response = repository.getCoffeeById(id)

                if (response.isSuccessful) {
                    _selectedCoffee.value =
                        response.body()?.toProduct(context)
                }
            } catch (e: Exception) {
                Log.e("CoffeeAPI", "ERROR", e)
            }
        }
    }

    fun getAllCoffee(context: Context) {

        Log.d("CoffeeAPI", "Function Called")

        viewModelScope.launch {

            try {

                Log.d("CoffeeAPI", "Before API")

                val response = repository.getAllCoffee()

                Log.d("CoffeeAPI", "After API")

                Log.d("CoffeeAPI", "Code = ${response.code()}")

                Log.d("CoffeeAPI", "Body = ${response.body()}")

                if (response.isSuccessful) {

                    _coffeeList.value =
                        response.body()?.map {
                            it.toProduct(context)
                        } ?: emptyList()

                    Log.d("CoffeeAPI", "Size = ${_coffeeList.value.size}")

                }

            } catch (e: Exception) {
                Log.e("CoffeeAPI", "ERROR", e)
            }

        }
    }
}