package com.moviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviles.restaurantes.models.Menu
import com.moviles.restaurantes.repositories.RestaurantRepository

class MenuViewModel : ViewModel() {
    private val _menuList: MutableLiveData<List<Menu>> by lazy {
        MutableLiveData<List<Menu>>()
    }

    val menuList: LiveData<List<Menu>> get() = _menuList

    fun fetchRestaurantMenu(id: Int) {
        RestaurantRepository.getRestaurantMenu(id,
            success = { menus ->
                _menuList.value = menus
            },
            failure = { error ->

            }
        )
    }
}