package com.moviles.restaurantes.repositories

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    fun saveAccessToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun deleteAccessToken() {
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()
    }
}