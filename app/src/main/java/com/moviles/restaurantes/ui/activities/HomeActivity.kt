package com.moviles.restaurantes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.restaurantes.R
import com.moviles.restaurantes.adapters.HomeRestaurantListAdapter
import com.moviles.restaurantes.databinding.ActivityHomeBinding
import com.moviles.restaurantes.models.SearchParameters
import com.moviles.restaurantes.repositories.SharedPrefManager
import com.moviles.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private val model: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefManager = SharedPrefManager(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val token = sharedPrefManager.getAccessToken()
        if(token!=null){
            model.fetchRestaurants(this)
        }else{
            model.fetchRestaurantsGuest(this)
        }


        setupListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }

    private fun setupViewModelListeners() {
        model.restaurantList.observe(this,{
            (binding.lstHomeRestaurante.adapter as HomeRestaurantListAdapter).updateData(it)
        })
    }

    private fun setupRecyclerView() {
       val adapter = HomeRestaurantListAdapter(arrayListOf())
        binding.lstHomeRestaurante.apply{
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupListeners() {
        binding.btnHomeLogOut.setOnClickListener{
            sharedPrefManager.deleteAccessToken()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.btnHomeUser.setOnClickListener{
            if(sharedPrefManager.getAccessToken()==null){
                Toast.makeText(this, "You must be logged in to access this feature", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener{
            val parameters = SearchParameters(
                selectedDate = binding.selectedDate.text.toString(),
                selectedTime = binding.startTime.text.toString(),
                endTime = binding.endTime.text.toString(),
                city = binding.city.text.toString()
            )
            Toast.makeText(this, "Searching restaurants in ${parameters.city}", Toast.LENGTH_SHORT).show()
            model.fetchRestaurantsByParameters(this, parameters)
        }

    }
}