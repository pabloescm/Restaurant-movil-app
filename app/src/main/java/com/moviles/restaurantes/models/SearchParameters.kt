package com.moviles.restaurantes.models



data class SearchParameters(
    val selectedDate: String,
    val selectedTime: String,
    val endTime: String,
    val city: String
)