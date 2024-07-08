package com.moviles.restaurantes.api



import com.moviles.restaurantes.models.Menu
import com.moviles.restaurantes.models.Restaurant
import com.moviles.restaurantes.models.SearchParameters
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface APIRestaurantService {
    @POST("search")
    fun searchRestaurants(@Header("Authorization") token: String): Call<List<Restaurant>>

    @POST("search")
    fun searchRestaurantsGuest(): Call<List<Restaurant>>

    @POST("search")
    fun searchRestaurantsByParameters(
        @Header("Authorization") token: String,
        @Body parameters: SearchParameters
    ): Call<List<Restaurant>>

    @GET("restaurants")
    fun getUserRestaurants(@Header("Authorization") token: String): Call<List<Restaurant>>

    //Get menu by restaurant id
    @GET("restaurants/{id}/menu")
    fun getRestaurantMenu(@Path("id") id: Int): Call<List<Menu>>

    @POST("restaurants")
    fun createRestaurant(@Header("Authorization") token: String, @Body restaurant: Restaurant): Call<Restaurant>

    /*
    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadLogo(@Path("id") id: Int, @Part file: MultipartBody.Part): Call<ResponseBody>
    */
    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadLogo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    @PUT("restaurants/{id}")
    fun updateRestaurant(@Header("Authorization") token: String, @Path("id") id: Int, @Body restaurant: Restaurant): Call<Restaurant>

    @DELETE("restaurants/{id}")
    fun deleteRestaurant(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>
}
