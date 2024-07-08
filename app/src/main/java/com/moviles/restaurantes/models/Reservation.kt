package com.moviles.restaurantes.models

data class Reservation(
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
    val status: String
)