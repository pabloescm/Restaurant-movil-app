package com.moviles.restaurantes.api

import com.moviles.restaurantes.models.Reservation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface APIReservationsService {

    @GET("restaurants/{id}/reservations")
    fun getReservations(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<Reservation>>





}