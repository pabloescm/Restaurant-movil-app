package com.moviles.restaurantes.repositories



import com.moviles.restaurantes.api.APIRestaurantService
import com.moviles.restaurantes.models.Menu
import com.moviles.restaurantes.models.Restaurant
import com.moviles.restaurantes.models.SearchParameters
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestaurantRepository {
    private val service: APIRestaurantService
    private val userService: APIRestaurantService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/restaurants/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APIRestaurantService::class.java)

        val userRetrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = userRetrofit.create(APIRestaurantService::class.java)
    }

    fun searchRestaurants(token: String, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        service.searchRestaurants("Bearer $token").enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Failed to fetch restaurants with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun searchRestaurantsGuest(success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        service.searchRestaurantsGuest().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Failed to fetch restaurants with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun searchRestaurantsByParameters(
        token: String, parameters: SearchParameters,
        success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit
    ) {
        service.searchRestaurantsByParameters("Bearer $token", parameters)
            .enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                    if (response.isSuccessful) {
                        success(response.body())
                    } else {
                        failure(Exception("Failed to fetch restaurants with response code ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun getUserRestaurants(
        token: String,
        success: (List<Restaurant>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        userService.getUserRestaurants("Bearer $token")
            .enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                    if (response.isSuccessful) {
                        success(response.body())
                    } else {
                        failure(Exception("Failed to fetch restaurants with response code ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun createRestaurant(
        token: String,
        restaurant: Restaurant,
        success: (Restaurant) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        userService.createRestaurant("Bearer $token", restaurant).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body()!!)
                } else {
                    failure(Exception("Failed to create restaurant with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateRestaurant(
        token: String,
        id: Int,
        restaurant: Restaurant,
        success: (Restaurant) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        userService.updateRestaurant("Bearer $token", id, restaurant).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body()!!)
                } else {
                    failure(Exception("Failed to update restaurant with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        userService.deleteRestaurant("Bearer $token", id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Failed to delete restaurant with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun uploadLogo(token: String, id: Int, file: MultipartBody.Part, success: () -> Unit, failure: (Throwable) -> Unit) {
        userService.uploadLogo("Bearer $token", id, file).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Failed to upload logo with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurantMenu(id: Int, success: (List<Menu>?) -> Unit, failure: (Throwable) -> Unit) {
        userService.getRestaurantMenu(id).enqueue(object : Callback<List<Menu>> {
            override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Failed to fetch menu with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                failure(t)
            }
        })
    }


}