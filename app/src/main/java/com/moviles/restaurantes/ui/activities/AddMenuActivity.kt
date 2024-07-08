package com.moviles.restaurantes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moviles.restaurantes.R
import com.moviles.restaurantes.databinding.ActivityAddMenuBinding
import com.moviles.restaurantes.databinding.ActivityAddRestaurantBinding
import com.moviles.restaurantes.models.MenuCategory
import com.moviles.restaurantes.repositories.MenuRepository
import com.moviles.restaurantes.repositories.SharedPrefManager

class AddMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val restaurantId = intent.getIntExtra("restaurantId", -1)
        Toast.makeText(this, "Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSubmitMenu.setOnClickListener {
            val menuName = binding.txtMenuName.text.toString()
            val restaurantId = intent.getIntExtra("restaurantId", -1).toString()
            val menuCategory = MenuCategory(menuName, restaurantId)
           // Toast.makeText(this, "Menu name: $menuName, Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()

            val sharedPrefManager = SharedPrefManager(this)
            val token = sharedPrefManager.getAccessToken()

            if (token != null) {
                MenuRepository.createMenuCategory(
                    token,
                    menuCategory,
                    success = {
                        Toast.makeText(this, "Menu category created successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    failure = { error ->
                        Toast.makeText(this, "Failed to create menu category: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Token is null", Toast.LENGTH_SHORT).show()
            }
        }
    }
}