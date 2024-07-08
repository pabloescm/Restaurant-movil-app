package com.moviles.restaurantes.api

import com.moviles.restaurantes.models.Auth
import com.moviles.restaurantes.models.LoginResponse
import com.moviles.restaurantes.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIAuthService {
    @POST("loginuser")
    fun login(@Body user: Auth): Call<LoginResponse>

    @POST("registeruser")
    fun register(@Body user: User): Call<LoginResponse>

}