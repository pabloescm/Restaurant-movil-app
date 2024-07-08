package com.moviles.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviles.restaurantes.models.Restaurant
import com.moviles.restaurantes.models.SearchParameters
import com.moviles.restaurantes.repositories.RestaurantRepository

import com.moviles.restaurantes.repositories.SharedPrefManager

class HomeViewModel:ViewModel() {
    private val _restaurantList:MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>()
    }

    val restaurantList: LiveData<List<Restaurant>> get() = _restaurantList

    fun fetchRestaurants(context: Context) {
        val sharedPrefManager = SharedPrefManager(context)
        val token = sharedPrefManager.getAccessToken()

        if (token != null) {
            RestaurantRepository.searchRestaurants(token,
                success = { restaurants ->
                    _restaurantList.value = restaurants
                },
                failure = { error ->
                    Toast.makeText(context, "Failed to fetch restaurants: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }else{
            Toast.makeText(context, "Token is null", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchRestaurantsGuest(context: Context) {
        RestaurantRepository.searchRestaurantsGuest(
            success = { restaurants ->
                _restaurantList.value = restaurants
            },
            failure = { error ->
                Toast.makeText(context, "Failed to fetch restaurants: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun fetchRestaurantsByParameters(context: Context, parameters: SearchParameters) {
        val sharedPrefManager = SharedPrefManager(context)
        val token = sharedPrefManager.getAccessToken()

        if (token != null) {
            RestaurantRepository.searchRestaurantsByParameters(
                token, parameters,
                success = { restaurants ->
                    _restaurantList.value = restaurants
                    Toast.makeText(context, "Fetched ${restaurants?.size} restaurants", Toast.LENGTH_SHORT).show()
                },
                failure = { error ->
                    Toast.makeText(context, "Failed to fetch restaurants: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(context, "Token is null", Toast.LENGTH_SHORT).show()
        }
    }


}