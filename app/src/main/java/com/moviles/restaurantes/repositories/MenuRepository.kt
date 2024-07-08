package com.moviles.restaurantes.repositories

import com.moviles.restaurantes.api.APIMenuService
import com.moviles.restaurantes.models.MenuCategory
import com.moviles.restaurantes.models.Plate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MenuRepository {
    private val service: APIMenuService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APIMenuService::class.java)
    }

    fun createMenuCategory(token: String, menuCategory: MenuCategory, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.createMenuCategory("Bearer $token", menuCategory).enqueue(object :
            Callback<MenuCategory> {
            override fun onResponse(call: Call<MenuCategory>, response: Response<MenuCategory>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Failed to create menu category with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<MenuCategory>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun createPlate(token: String, plate: Plate, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.createPlate("Bearer $token", plate).enqueue(object :
            Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Failed to create plate with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Plate>, t: Throwable) {
                failure(t)
            }
        })
    }

}