package com.moviles.restaurantes.models

data class Menu(
    val id: Int,
    val name: String,
    val restaurant_id: Int,
    val plates: List<Plate>
)