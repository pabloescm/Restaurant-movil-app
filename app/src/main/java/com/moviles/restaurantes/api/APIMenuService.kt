package com.moviles.restaurantes.api

import com.moviles.restaurantes.models.MenuCategory
import com.moviles.restaurantes.models.Plate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIMenuService {
    @POST("menu-categories")
    fun createMenuCategory(@Header("Authorization") token: String, @Body menuCategory: MenuCategory): Call<MenuCategory>

    @POST("plates")
    fun createPlate(@Header("Authorization") token: String, @Body plate: Plate): Call<Plate>
}