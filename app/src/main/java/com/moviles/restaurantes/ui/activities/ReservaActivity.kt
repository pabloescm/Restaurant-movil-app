package com.moviles.restaurantes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.restaurantes.R
import com.moviles.restaurantes.adapters.ReservationsAdapter

import com.moviles.restaurantes.databinding.ActivityReservaBinding
import com.moviles.restaurantes.repositories.ReservationsRepository
import com.moviles.restaurantes.repositories.SharedPrefManager

class ReservaActivity : AppCompatActivity() {
    lateinit var binding: ActivityReservaBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        fetchReservations()

    }

    private fun fetchReservations() {
        val sharedPrefManager = SharedPrefManager(this)
        val token = sharedPrefManager.getAccessToken()
        val restaurantId = intent.getIntExtra("restaurantId", -1)
        Toast.makeText(this, "Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()

        if (token != null) {
            ReservationsRepository.getReservations(
                token,
                restaurantId,
                success = { reservations ->
                    (binding.lstReservation.adapter as ReservationsAdapter).updateData(reservations ?: emptyList())
                         // Toast.makeText(this, "Reservations fetched successfully", Toast.LENGTH_SHORT).show()
                },
                failure = { error ->
                    Toast.makeText(this, "  ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(this, "Token is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        val adapter = ReservationsAdapter(arrayListOf())
        binding.lstReservation.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ReservaActivity)
        }
    }
}