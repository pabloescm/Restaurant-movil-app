package com.moviles.restaurantes.repositories

import android.content.Context
import com.moviles.restaurantes.api.APIAuthService
import com.moviles.restaurantes.models.Auth
import com.moviles.restaurantes.models.LoginResponse
import com.moviles.restaurantes.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AuthRepository {

    fun login(user: Auth, context: Context, success: (LoginResponse?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIAuthService = retrofit.create(APIAuthService::class.java)
        service.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Guardo el token en SharedPreferences
                    val sharedPrefManager = SharedPrefManager(context)
                    sharedPrefManager.saveAccessToken(response.body()?.access_token ?: "")
                    success(response.body())
                } else {
                    failure(Exception("Login failed with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                failure(t)
                t.printStackTrace()
            }
        })
    }

    fun register(user: User, success: (LoginResponse?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(APIAuthService::class.java)
        service.register(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Register failed with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                failure(t)
                t.printStackTrace()
            }
        })
    }



}