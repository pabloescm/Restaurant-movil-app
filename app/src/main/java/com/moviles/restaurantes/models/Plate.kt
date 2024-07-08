package com.moviles.restaurantes.models

data class Plate(
    val id: Int?=null,
    val name: String,
    val description: String,
    val price: String,
    val menu_category_id: Int
)
