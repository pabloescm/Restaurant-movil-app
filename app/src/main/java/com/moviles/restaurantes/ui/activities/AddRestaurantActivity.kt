package com.moviles.restaurantes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moviles.restaurantes.R
import com.moviles.restaurantes.databinding.ActivityAddRestaurantBinding
import com.moviles.restaurantes.models.Restaurant
import com.moviles.restaurantes.repositories.RestaurantRepository
import com.moviles.restaurantes.repositories.SharedPrefManager

class AddRestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRestaurantBinding
    private var isEditing: Boolean = false
    private var existingRestaurant: Restaurant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupListeners()
        isEditing = intent.getBooleanExtra("isEditing", false)
        if (isEditing) {
            existingRestaurant = intent.getSerializableExtra("restaurant") as Restaurant
            populateFields(existingRestaurant)
        }



    }

    private fun setupListeners() {
        binding.submitRestaurant.setOnClickListener {
            val name = binding.restaurantName.text.toString()
            val address = binding.restaurantAddress.text.toString()
            val city = binding.restaurantCity.text.toString()
            val description = binding.restaurantDescription.text.toString()

            val restaurant = Restaurant(name = name, address = address, city = city, description = description)

            // Get the token from SharedPrefManager
            val sharedPrefManager = SharedPrefManager(this)
            val token = sharedPrefManager.getAccessToken()

            if (token != null) {
                if (isEditing) {
                    existingRestaurant?.let {
                        RestaurantRepository.updateRestaurant(token, it.id!!, restaurant,
                            success = {
                                Toast.makeText(this, "Restaurant updated successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            },
                            failure = { error ->
                                Toast.makeText(this, "Failed to update restaurant: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                } else {
                    RestaurantRepository.createRestaurant(token, restaurant,
                        success = {
                            Toast.makeText(this, "Restaurant added successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        failure = { error ->
                            Toast.makeText(this, "Failed to add restaurant: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            } else {
                Toast.makeText(this, "Token is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateFields(restaurant: Restaurant?) {
        binding.restaurantName.setText(restaurant?.name)
        binding.restaurantAddress.setText(restaurant?.address)
        binding.restaurantCity.setText(restaurant?.city)
        binding.restaurantDescription.setText(restaurant?.description)
    }
}