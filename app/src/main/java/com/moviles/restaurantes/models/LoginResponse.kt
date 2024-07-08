package com.moviles.restaurantes.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val access_token: String
)