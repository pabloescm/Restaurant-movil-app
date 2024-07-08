package com.moviles.restaurantes.repositories



import com.moviles.restaurantes.api.APIReservationsService
import com.moviles.restaurantes.models.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReservationsRepository {
    fun getReservations(token: String, id: Int, success: (List<Reservation>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIReservationsService = retrofit.create(APIReservationsService::class.java)
        service.getReservations("Bearer $token", id).enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Exception("Failed to fetch reservations with response code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
                t.printStackTrace()
            }
        })
    }
}