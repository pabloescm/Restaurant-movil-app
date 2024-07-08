package com.moviles.restaurantes.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(
    @SerializedName("id")
    val id: Int?= null,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("user_id")
    val userId: Int?= null,

    @SerializedName("logo")
    val logo: String?=null
): Serializable